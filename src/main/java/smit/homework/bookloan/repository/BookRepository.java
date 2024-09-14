package smit.homework.bookloan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smit.homework.bookloan.entity.Book;

/**
 * Repository interface for Book entity.
 * Provides CRUD operations through JpaRepository.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 14.09.2024
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
