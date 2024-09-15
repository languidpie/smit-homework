package smit.homework.bookloan.controller.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Mari-Liis Pihlapuu
 * Date: 15.09.2024
 */
@Data
public class BookReserveForm {
    @NotBlank(message = "Recipient is mandatory")
    private String recipient;
}
