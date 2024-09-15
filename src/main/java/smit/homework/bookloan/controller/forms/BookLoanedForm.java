package smit.homework.bookloan.controller.forms;

import jakarta.validation.constraints.NotBlank;
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
public class BookLoanedForm {
    @NotBlank(message = "Recipient is mandatory")
    private String recipient;
    @NotBlank(message = "Book return date (bookReturnAt) is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime bookReturnAt;
}
