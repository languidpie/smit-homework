package smit.homework.bookloan.controller.forms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Form object for loaning out a book.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 15.09.2024
 */
@Data
@Schema(description = "Form for loaning out a book")
public class BookLoanedForm {
    @Schema(description = "The recipient of the loaned book", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Recipient is mandatory")
    private String recipient;

    @Schema(description = "The date and time when the book is expected to be returned", example = "2024-10-01T15:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Book return date (bookReturnAt) is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime bookReturnAt;
}
