package smit.homework.bookloan.controller;

import lombok.Data;

/**
 * A form object representing the data needed to create or update a Book entity.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 14.09.2024
 */
//TODO: Add validations
@Data
public class BookFrom {
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private int year;
    private String genre;
}
