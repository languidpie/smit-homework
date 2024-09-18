package smit.homework.bookloan.controller.forms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * A form object representing the data needed to create or update a Book entity.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 14.09.2024
 */
@Data
@Schema(description = "Form for creating or updating a book")
public class BookForm {
    @Schema(description = "The title of the book", example = "Jane Eyre", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Title is mandatory")
    private String title;

    @Schema(description = "The author of the book", example = "Charlotte Brontë", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Author is mandatory")
    private String author;

    @Schema(description = "The publisher of the book", example = "Smith, Elder & Co.")
    private String publisher;

    @Schema(description = "The ISBN of the book", example = "978-3-16-148410-0")
    private String isbn;

    @Schema(description = "The year the book was published", example = "1847")
    private int year;

    @Schema(description = "The genre of the book", example = "Romance")
    private String genre;
}
