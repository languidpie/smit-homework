package smit.homework.bookloan.service;

import smit.homework.bookloan.controller.BookForm;
import smit.homework.bookloan.controller.BookStatusForm;
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

    /**
     * Creates and saves a new book using the provided book form data.
     *
     * @param bookFormData the form data for the new book
     * @return the newly created book
     */
    Book saveNewBook(BookForm bookFormData);

    /**
     * Updates an existing book with the provided form data.
     *
     * @param id identifier of the book to be updated
     * @param bookFormData the form data to update the book with
     * @return the updated book
     * @throws RuntimeException if the book with the given ID is not found
     */
    Book updateBook(long id, BookForm bookFormData);

    /**
     * Find a book by its id.
     *
     * @param id the identifier of the book
     * @return an Optional containing the found book, or an empty Optional if no book is found
     */
    Optional<Book> findBookById(long id);

    /**
     * Fetches all existing Book entities.
     *
     * @return list of all Book entities
     */
    List<Book> findAllBooks();

    /**
     * Deletes a Book entity by its id.
     * @param id {@link Book} identifier of a book to be deleted
     */
    void deleteBook(long id);

    /**
     * Loans a book to a recipient.
     *
     * @param id the identifier of the book
     * @param bookStatusForm the form data for the operation
     */
    void loanBook(long id, BookStatusForm bookStatusForm);

    /**
     * Reserves a book to a recipient.
     *
     * @param id the identifier of the book
     * @param bookStatusForm the form data for the operation
     */
    void reserveBook(long id, BookStatusForm bookStatusForm);

    /**
     * Returns a book, making it available again.
     *
     * @param id the identifier of the book
     */
    void returnBook(long id);
}
