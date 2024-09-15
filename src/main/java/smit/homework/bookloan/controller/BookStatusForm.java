package smit.homework.bookloan.controller;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author Mari-Liis Pihlapuu
 * Date: 15.09.2024
 */
@Data
public class BookStatusForm {
    private String recipient;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime bookReturnAt;
}
