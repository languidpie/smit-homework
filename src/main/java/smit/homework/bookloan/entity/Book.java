package smit.homework.bookloan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Mari-Liis Pihlapuu
 * Date: 14.09.2024
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator",sequenceName = "BOOK_SEQ", allocationSize = 1)
    private long id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private int year;
    private String genre;
    private BookStatus status;
    private String recipient;
    @Column(name = "book_return_at")
    private LocalDateTime bookReturnAt;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public enum BookStatus {
        AVAILABLE,
        LOANED_OUT,
        RESERVED
    }
}
