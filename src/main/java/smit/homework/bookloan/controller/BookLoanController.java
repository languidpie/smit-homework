package smit.homework.bookloan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mari-Liis Pihlapuu
 * Date: 14.09.2024
 */
@RestController
@RequestMapping("/api")
public class BookLoanController {

    @GetMapping("/customers")
    public String greet() {
        return "Hello World";
    }
}
