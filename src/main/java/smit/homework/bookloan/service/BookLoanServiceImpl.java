package smit.homework.bookloan.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smit.homework.bookloan.controller.BookForm;
import smit.homework.bookloan.controller.BookStatusForm;
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
        Optional<Book> existingBook = this.findBookById(id);

        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(bookFormData.getTitle());
            book.setAuthor(bookFormData.getAuthor());
            book.setPublisher(bookFormData.getPublisher());
            book.setIsbn(bookFormData.getIsbn());
            book.setYear(bookFormData.getYear());
            book.setGenre(bookFormData.getGenre());
            book.setUpdatedAt(LocalDateTime.now());
            return this.saveBook(book);
        } else {
            //TODO create a custom exception that will be better handled by FE
            throw new RuntimeException();
        }
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
    public void loanBook(long id, BookStatusForm bookStatusForm) {
        this.updateBookStatus(id, bookStatusForm, Book.BookStatus.LOANED_OUT);
    }

    @Override
    public void reserveBook(long id, BookStatusForm bookStatusForm) {
        this.updateBookStatus(id, bookStatusForm, Book.BookStatus.RESERVED);
    }

    @Override
    public void returnBook(long id) {
        this.updateBookStatus(id, new BookStatusForm(), Book.BookStatus.AVAILABLE);
    }

    // PRIVATE METHODS

    private void updateBookStatus(long bookId, BookStatusForm bookStatusForm, Book.BookStatus newStatus) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            Book bookToBeLoaned = book.get();
            bookToBeLoaned.setStatus(newStatus);
            bookToBeLoaned.setRecipient(bookStatusForm.getRecipient());
            bookToBeLoaned.setBookReturnAt(bookStatusForm.getBookReturnAt());
            bookToBeLoaned.setUpdatedAt(LocalDateTime.now());

            this.saveBook(bookToBeLoaned);
        } else {
            //TODO: throw exception??
            throw new RuntimeException();
        }
    }
}
