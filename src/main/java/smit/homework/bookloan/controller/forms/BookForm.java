package smit.homework.bookloan.controller.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * A form object representing the data needed to create or update a Book entity.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 14.09.2024
 */
@Data
public class BookForm {
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "Author is mandatory")
    private String author;
    private String publisher;
    private String isbn;
    private int year;
    private String genre;
}
