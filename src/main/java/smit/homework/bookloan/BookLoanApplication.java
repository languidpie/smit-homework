package smit.homework.bookloan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Book Loan application.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 14.09.2024
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Book Loan API", version = "1.0", description = "Book loaning management API"))
public class BookLoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookLoanApplication.class, args);
	}

}
