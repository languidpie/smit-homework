package smit.homework.bookloan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/api/books")
@Tag(name = "Book Loan", description = "API for managing book loaning")
public class BookLoanController {

    @Autowired
    private BookLoanService bookLoanService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @Operation(
            summary = "Get all books",
            description = "Retrieves a list of all books in the library.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the list of books",
                            content = @Content(
                                    schema = @Schema(implementation = Book.class),
                                    examples = {
                                            @ExampleObject(name = "Books List Example", value = "[{\"id\": 8, \"title\": \"Jane Eyre\", \"author\": \"Charlotte Brontë\", \"publisher\": \"Smith, Elder & Co.\", " +
                                                    "\"isbn\": \"978-3-16-148410-0\", \"year\": 1847, \"genre\": \"Romance\", \"status\": \"AVAILABLE\", \"recipient\": null, \"bookReturnAt\": null, " +
                                                    "\"createdAt\": \"2024-09-17T15:34:56.479678\", \"updatedAt\": null}]"),
                                            @ExampleObject(name = "No Books Example", value = "[]")
                                    }
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks() {
        log.info("Searching for all books.");

        List<Book> books = bookLoanService.findAllBooks();

        log.info("Number of books found: {}", books.size());

        return ResponseEntity.ok(books);
    }

    @Operation(
            summary = "Get a book by ID",
            description = "Fetch the details of a specific book by its ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book found successfully",
                            content = @Content(
                                    schema = @Schema(implementation = Book.class),
                                    examples = @ExampleObject(
                                            name = "Book Example",
                                            value = "{\"id\": 8, \"title\": \"Jane Eyre\", \"author\": \"Charlotte Brontë\", " +
                                                    "\"publisher\": \"Smith, Elder & Co.\", \"isbn\": \"978-3-16-148410-0\", " +
                                                    "\"year\": 1847, \"genre\": \"Romance\", \"status\": \"AVAILABLE\", \"recipient\": null, " +
                                                    "\"bookReturnAt\": null, \"createdAt\": \"2024-09-17T15:34:56.479678\", \"updatedAt\": null}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable long id) {
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

    @Operation(
            summary = "Create a new book",
            description = "Creates a new book in the system with the given title, author, and other details.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "BookForm containing the details of the new book",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = BookForm.class),
                            examples = @ExampleObject(
                                    name = "BookForm Example",
                                    value = """
                                            {
                                              "title": "Jane Eyre",
                                              "author": "Charlotte Brontë",
                                              "publisher": "Smith, Elder & Co.",
                                              "isbn": "978-3-16-148410-0",
                                              "year": 1847,
                                              "genre": "Romance"
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "New book created successfully",
                            content = @Content(
                                    schema = @Schema(implementation = Book.class),
                                    examples = @ExampleObject(
                                            name = "Book Example",
                                            value = """
                                                    {
                                                      "id": 8,
                                                      "title": "Jane Eyre",
                                                      "author": "Charlotte Brontë",
                                                      "publisher": "Smith, Elder & Co.",
                                                      "isbn": "978-3-16-148410-0",
                                                      "year": 1847,
                                                      "genre": "Romance",
                                                      "status": "AVAILABLE",
                                                      "createdAt": "2024-09-17T15:34:56.479678"
                                                    }"""
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid book form data",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Bad Request Example",
                                            value = """
                                                    {
                                                      "author": "Author is mandatory",
                                                      "title": "Title is mandatory"
                                                    }"""
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Book> saveNewBook(@RequestBody @Valid BookForm bookForm) {
        log.info("Attempting to save book. Title={}, author={}", bookForm.getTitle(), bookForm.getAuthor());

        Book newBook = bookLoanService.saveNewBook(bookForm);

        log.info("New book id={}", newBook.getId());
        return ResponseEntity.ok(newBook);
    }


    @Operation(
            summary = "Update an existing book",
            description = "Updates the details of an existing book by its ID with the provided new information in the BookForm.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "BookForm containing the updated details of the book",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = BookForm.class),
                            examples = @ExampleObject(
                                    name = "BookForm Example",
                                    value = """
                                            {
                                              "title": "Jane Eyre",
                                              "author": "Charlotte Brontë",
                                              "publisher": "Smith, Elder & Co.",
                                              "isbn": "978-3-16-148410-0",
                                              "year": 1847,
                                              "genre": "Romance"
                                            }"""
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book updated successfully",
                            content = @Content(
                                    schema = @Schema(implementation = Book.class),
                                    examples = @ExampleObject(
                                            name = "Updated Book Example",
                                            value = """
                                                    {
                                                      "id": 8,
                                                      "title": "Jane Eyre",
                                                      "author": "Charlotte Brontë",
                                                      "publisher": "Smith, Elder & Co.",
                                                      "isbn": "978-3-16-148410-0",
                                                      "year": 1847,
                                                      "genre": "Romance",
                                                      "status": "AVAILABLE",
                                                      "createdAt": "2024-09-17T15:34:56.479678",
                                                      "updatedAt": "2024-09-18T10:25:00.123456"
                                                    }"""
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Book Not Found Example",
                                            value = """
                                                    {
                                                      "message": "Book not found"
                                                    }"""
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}/edit")
    public ResponseEntity<Book> updateBook(@PathVariable long id, @RequestBody BookForm bookForm) {
        log.info("Attempting to update book with id={}.", id);

        Book updatedBook = bookLoanService.updateBook(id, bookForm);

        log.info("Book with id={} updated.", updatedBook.getId());

        return ResponseEntity.ok(updatedBook);
    }

    @Operation(
            summary = "Reserve a book",
            description = "Reserves a book by its ID, updating its status to RESERVED if the operation is successful.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "BookReserveForm containing the reservation details",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = BookReserveForm.class),
                            examples = @ExampleObject(
                                    name = "BookReserveForm Example",
                                    value = "{\n" +
                                            "  \"recipient\": \"John Doe\"" +
                                            "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book reserved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid reservation details",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Invalid Reservation Example",
                                            value = """
                                                    {
                                                      "recipient": "Recipient is mandatory"
                                                    }"""
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Book Not Found Example",
                                            value = """
                                                    {
                                                      "message": "Book not found"
                                                    }"""
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}/reserve")
    public void reserveBook(@PathVariable long id, @RequestBody @Valid BookReserveForm bookReserveForm) {
        log.info("Attempting to reserve book with id={}.", id);

        bookLoanService.reserveBook(id, bookReserveForm);

        log.info("Book with id={} status set as RESERVED.", id);
    }

    @Operation(
            summary = "Loan out a book",
            description = "Loans out a book by its ID, updating its status to LOANED_OUT if the operation is successful.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "BookLoanedForm containing the loaning details",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = BookLoanedForm.class),
                            examples = @ExampleObject(
                                    name = "BookLoanedForm Example",
                                    value = "{\n" +
                                            "  \"recipient\": \"John Doe\"," +
                                            "  \"bookReturnAt\": \"2024-09-16T14:41:19.971+03:00\"" +
                                    "}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book loaned out successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid loaning details",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Invalid Loan Out Example",
                                            value = """
                                                    {
                                                      "recipient": "Recipient is mandatory",
                                                      "bookReturnAt": "Book return date (bookReturnAt) is mandatory"
                                                    }"""
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Book Not Found Example",
                                            value = """
                                                    {
                                                      "message": "Book not found"
                                                    }"""
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}/loan")
    public void loanBook(@PathVariable long id, @RequestBody @Valid BookLoanedForm bookLoanedForm) {
        log.info("Attempting to loan book with id={}.", id);

        bookLoanService.loanBook(id, bookLoanedForm);

        log.info("Book with id={} status set as LOANED_OUT.", id);
    }

    @Operation(
            summary = "Returning a book",
            description = "Returns a book by its ID, updating its status to AVAILABLE if the operation is successful.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book returned successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Book Not Found Example",
                                            value = """
                                                    {
                                                      "message": "Book not found"
                                                    }"""
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}/return")
    public void returnBook(@PathVariable long id) {
        log.info("Attempting to return book with id={}.", id);

        bookLoanService.returnBook(id);

        log.info("Book with id={} status set as AVAILABLE.", id);
    }

    @Operation(
            summary = "Receiving a book",
            description = "Marks a book as received by its ID, updating its status to RECEIVED if the operation is successful.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book received successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Book not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Book Not Found Example",
                                            value = """
                                                    {
                                                      "message": "Book not found"
                                                    }"""
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}/received")
    public void receivedBook(@PathVariable long id) {
        log.info("Attempting to receive book with id={}.", id);

        bookLoanService.markAsReceived(id);
        log.info("Book with id={} status set as RECEIVED.", id);
    }

    @Operation(
            summary = "Deleting a book",
            description = "Deletes a book by its ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Book deleted successfully if it existed"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id) {
        log.info("Attempting to delete book with id={}.", id);

        bookLoanService.deleteBook(id);

        log.info("Book with id={} deleted.", id);
    }

}
