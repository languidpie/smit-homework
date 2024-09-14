package smit.homework.bookloan.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smit.homework.bookloan.entity.Book;
import smit.homework.bookloan.service.BookLoanService;

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

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks() {
        log.info("Attempting to find all books.");

        return ResponseEntity.ok(bookLoanService.findAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Integer id) {
        log.info("Attempting to find book by id.");

        return ResponseEntity.ok(bookLoanService.findBookById(id).get());
    }

    @PostMapping
    public ResponseEntity<Book> saveNewBook(@RequestBody @Valid BookFrom book) {
        log.info("Attempting to save book.");

        return ResponseEntity.ok(bookLoanService.saveNewBook(book));
    }

    @PutMapping("/{id}/reserve")
    public ResponseEntity<Book> reserveBook(@PathVariable long id) {
        log.info("Attempting to reserve book.");
        // TODO: to be implemented
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/loan")
    public ResponseEntity<Book> loanBook(@PathVariable long id) {
        log.info("Attempting to loan book.");
        // TODO: to be implemented
        return ResponseEntity.noContent().build();
    }
}
