package smit.homework.bookloan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

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
    private long id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private int year;
    private String genre;
    private BookStatus status;
    private String recipient;
    private Timestamp bookReturnDate;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public enum BookStatus {
        AVAILABLE,
        LOANED_OUT,
        RESERVED
    }
}
