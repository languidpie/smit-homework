package smit.homework.bookloan.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import smit.homework.bookloan.controller.forms.BookForm;
import smit.homework.bookloan.controller.forms.BookLoanedForm;
import smit.homework.bookloan.controller.forms.BookReserveForm;
import smit.homework.bookloan.entity.Book;
import smit.homework.bookloan.service.BookLoanService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * REST controller for processing book loan related requests.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 14.09.2024
 */
@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookLoanController {

    @Autowired
    private BookLoanService bookLoanService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks() {
        log.info("Searching for all books.");

        List<Book> books = bookLoanService.findAllBooks();

        log.info("Number of books found: {}", books.size());

        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Integer id) {
        log.info("Searching for book by id={}", id);

        return bookLoanService.findBookById(id)
                .map(book -> {
                    log.info("Found book with id={}", book.getId());
                    return ResponseEntity.ok(book);
                })
                .orElseGet(() -> {
                    log.info("No book with id={}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Book> saveNewBook(@RequestBody @Valid BookForm bookForm) {
        log.info("Attempting to save book. Title={}, author={}", bookForm.getTitle(), bookForm.getAuthor());

        Book newBook = bookLoanService.saveNewBook(bookForm);

        log.info("New book id={}", newBook.getId());
        return ResponseEntity.ok(newBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody @Valid BookForm bookForm) {
        log.info("Attempting to update book with id={}.", id);

        Book updatedBook = bookLoanService.updateBook(id, bookForm);

        log.info("Book with id={} updated.", updatedBook.getId());

        return ResponseEntity.ok(updatedBook);
    }

    @PutMapping("/{id}/reserve")
    public void reserveBook(@PathVariable long id, @RequestBody BookReserveForm bookReserveForm) {
        log.info("Attempting to reserve book with id={}.", id);

        bookLoanService.reserveBook(id, bookReserveForm);

        log.info("Book with id={} status set as RESERVED.", id);
    }

    @PutMapping("/{id}/loan")
    public void loanBook(@PathVariable long id, @RequestBody BookLoanedForm bookLoanedForm) {
        log.info("Attempting to loan book with id={}.", id);

        bookLoanService.loanBook(id, bookLoanedForm);

        log.info("Book with id={} status set as LOANED_OUT.", id);
    }

    @PutMapping("/{id}/return")
    public void returnBook(@PathVariable long id) {
        log.info("Attempting to return book with id={}.", id);

        bookLoanService.returnBook(id);

        log.info("Book with id={} status set as AVAILABLE.", id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id) {
        log.info("Attempting to delete book with id={}.", id);

        bookLoanService.deleteBook(id);

        log.info("Book with id={} deleted.", id);
    }
}
