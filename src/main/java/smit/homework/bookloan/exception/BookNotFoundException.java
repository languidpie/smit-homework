package smit.homework.bookloan.exception;

/**
 * Exception thrown when a book is not found in the book loan management system.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 17.09.2024
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super("Book not found");
    }
}
