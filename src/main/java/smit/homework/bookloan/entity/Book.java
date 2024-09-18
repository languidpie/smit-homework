package smit.homework.bookloan.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents Book entity in the application.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 14.09.2024
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents a book in the loan management system")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator",sequenceName = "BOOK_SEQ", allocationSize = 1)
    @Schema(description = "Unique identifier for the book", example = "1")
    private long id;

    @Schema(description = "Title of the book", example = "Jane Eyre")
    private String title;

    @Schema(description = "Author of the book", example = "Charlotte Brontë")
    private String author;

    @Schema(description = "Publisher of the book", example = "Smith, Elder & Co.")
    private String publisher;

    @Schema(description = "ISBN number of the book", example = "978-3-16-148410-0")
    private String isbn;

    @Schema(description = "Publication year of the book", example = "1847")
    private int year;

    @Schema(description = "Genre of the book", example = "Romance")
    private String genre;

    @Schema(description = "Current status of the book", example = "AVAILABLE", allowableValues = {"AVAILABLE", "LOANED_OUT", "RESERVED", "RECEIVED"})
    private BookStatus status;

    @Schema(description = "Person to whom the book is loaned or reserved", example = "John Doe")
    private String recipient;

    @Column(name = "book_return_at")
    @Schema(description = "Date and time when the book is expected to be returned", example = "2024-10-01T15:30:00")
    private LocalDateTime bookReturnAt;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    @Schema(description = "Timestamp when the book record was created", example = "2024-09-17T15:34:56")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Schema(description = "Timestamp when the book record was last updated", example = "2024-09-18T12:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "Enumeration representing the book status")
    public enum BookStatus {
        @Schema(description = "Book is available for loan or reservation")
        AVAILABLE,

        @Schema(description = "Book is currently loaned out")
        LOANED_OUT,

        @Schema(description = "Book is reserved")
        RESERVED,

        @Schema(description = "Book has been received")
        RECEIVED
    }
}
