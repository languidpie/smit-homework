package smit.homework.bookloan.service;

import smit.homework.bookloan.entity.Book;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for {@link Book} entity.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 14.09.2024
 */
public interface BookLoanService {

    /**
     * Saves or updates a Book entity.
     * @param book {@link Book} entity to save
     * @return the saved book
     */
    Book saveBook(Book book);

    Optional<Book> findBookById(long id);

    /**
     * Fetches all existing Book entities.
     *
     * @return list of all Book entities
     */
    List<Book> findAllBooks();

    /**
     * Deletes a Book entity.
     * @param id {@link Book} identifier of a book to be deleted
     */
    void deleteBook(long id);

    void loanBook(long bookId);

    void reserveBook(long bookId);

    void removeReservation(long bookId);
}
