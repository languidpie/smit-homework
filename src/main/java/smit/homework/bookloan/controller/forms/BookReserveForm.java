package smit.homework.bookloan.controller.forms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Form object for reserving a book.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 15.09.2024
 */
@Data
@Schema(description = "Form for reserving a book")
public class BookReserveForm {
    @Schema(description = "The person reserving the book", example = "Commander Shepard", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Recipient is mandatory")
    private String recipient;
}
