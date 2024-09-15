package smit.homework.bookloan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smit.homework.bookloan.controller.forms.BookForm;
import smit.homework.bookloan.controller.forms.BookLoanedForm;
import smit.homework.bookloan.controller.forms.BookReserveForm;
import smit.homework.bookloan.entity.Book;
import smit.homework.bookloan.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of BookLoanService.
 * Provides business logic for managing the book loaning operations.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 14.09.2024
 */
@Slf4j
@Service
@Transactional
public class BookLoanServiceImpl implements BookLoanService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book saveNewBook(BookForm bookFormData) {
        Book newBook = Book.builder()
                .title(bookFormData.getTitle())
                .author(bookFormData.getAuthor())
                .publisher(bookFormData.getPublisher())
                .isbn(bookFormData.getIsbn())
                .year(bookFormData.getYear())
                .genre(bookFormData.getGenre())
                .status(Book.BookStatus.AVAILABLE)
                .build();

        return this.saveBook(newBook);
    }

    @Override
    public Book updateBook(long id, BookForm bookFormData) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(bookFormData.getTitle());
                    book.setAuthor(bookFormData.getAuthor());
                    book.setPublisher(bookFormData.getPublisher());
                    book.setIsbn(bookFormData.getIsbn());
                    book.setYear(bookFormData.getYear());
                    book.setGenre(bookFormData.getGenre());
                    book.setUpdatedAt(LocalDateTime.now());
                    return this.saveBook(book);
                }).orElseThrow(RuntimeException::new);
    }

    @Override
    public Optional<Book> findBookById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void loanBook(long id, BookLoanedForm bookLoanedForm) {
        bookRepository.findById(id)
                .map(book -> {
                    book.setStatus(Book.BookStatus.LOANED_OUT);
                    book.setRecipient(bookLoanedForm.getRecipient());
                    book.setBookReturnAt(bookLoanedForm.getBookReturnAt());
                    book.setUpdatedAt(LocalDateTime.now());

                    return this.saveBook(book);
                }).orElseThrow(RuntimeException::new);
    }

    @Override
    public void reserveBook(long id, BookReserveForm bookReserveForm) {
        bookRepository.findById(id)
                .map(book -> {
                    book.setStatus(Book.BookStatus.RESERVED);
                    book.setRecipient(bookReserveForm.getRecipient());
                    book.setUpdatedAt(LocalDateTime.now());

                    return this.saveBook(book);
                }).orElseThrow(RuntimeException::new);
    }

    @Override
    public void returnBook(long id) {
        bookRepository.findById(id)
                .map(book -> {
                    book.setStatus(Book.BookStatus.AVAILABLE);
                    book.setRecipient(null);
                    book.setBookReturnAt(null);
                    book.setUpdatedAt(LocalDateTime.now());

                    return this.saveBook(book);
                }).orElseThrow(RuntimeException::new);
    }
}
