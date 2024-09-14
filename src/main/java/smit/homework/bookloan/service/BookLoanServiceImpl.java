package smit.homework.bookloan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smit.homework.bookloan.entity.Book;
import smit.homework.bookloan.repository.BookRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of BookLoanService.
 * Provides business logic for managing the book loaning operations.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 14.09.2024
 */
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
    public void loanBook(long bookId) {
        this.updateBookStatus(bookId, Book.BookStatus.LOANED_OUT);
    }

    @Override
    public void reserveBook(long bookId) {
        this.updateBookStatus(bookId, Book.BookStatus.RESERVED);
    }

    @Override
    public void removeReservation(long bookId) {
        this.updateBookStatus(bookId, Book.BookStatus.AVAILABLE);
    }

    // PRIVATE METHODS

    private void updateBookStatus(long bookId, Book.BookStatus newStatus) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            Book bookToBeLoaned = book.get();
            bookToBeLoaned.setStatus(newStatus);

            this.saveBook(bookToBeLoaned);
        } else {
            //TODO: throw exception??
            throw new RuntimeException();
        }
    }
}
