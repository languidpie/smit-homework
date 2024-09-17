package smit.homework.bookloan.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import smit.homework.bookloan.controller.forms.BookForm;
import smit.homework.bookloan.controller.forms.BookLoanedForm;
import smit.homework.bookloan.controller.forms.BookReserveForm;
import smit.homework.bookloan.entity.Book;
import smit.homework.bookloan.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link BookLoanServiceImpl} class.
 *
 * @author Mari-Liis Pihlapuu
 * Date: 17.09.2024
 */
@ExtendWith(MockitoExtension.class)
class BookLoanServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookLoanServiceImpl bookLoanService;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = Book.builder()
                .id(1)
                .title("To Kill a Mockingbird")
                .author("Harper Lee")
                .publisher("J.B. Lippincott & Co.")
                .isbn("978-0-06-112008-4")
                .status(Book.BookStatus.AVAILABLE)
                .year(1960)
                .genre("Fiction")
                .recipient("my friend")
                .build();
    }

    @Test
    void should_save_book() {
        // given
        given(bookRepository.save(this.book)).willReturn(this.book);

        // when
        Book savedBook = bookLoanService.saveBook(this.book);

        // then
        assertThat(savedBook)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(this.book);
    }

    @Test
    void should_save_book_based_on_form_data() {
        // given
        BookForm bookForm = new BookForm();
        bookForm.setTitle("To Kill a Mockingbird");
        bookForm.setAuthor("Harper Lee");
        bookForm.setPublisher("J.B. Lippincott & Co.");
        bookForm.setIsbn("978-0-06-112008-4");
        bookForm.setYear(1960);
        bookForm.setGenre("Fiction");

        given(bookRepository.save(any(Book.class))).willReturn(this.book);

        // when
        Book newBook = bookLoanService.saveNewBook(bookForm);

        // then
        assertThat(newBook)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(this.book);
    }

    @Test
    void should_update_book() {
        // given
        BookForm bookForm = new BookForm();
        bookForm.setTitle("To Kill a Bird");

        given(bookRepository.findById(anyLong())).willReturn(Optional.of(this.book));

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        when(bookRepository.save(captor.capture())).thenReturn(this.book);

        // when
        Book updatedBook = bookLoanService.updateBook(1, bookForm);

        // then
        assertThat(updatedBook)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(this.book);

        assertThat(captor.getValue().getUpdatedAt())
                .isNotNull();
        assertThat(captor.getValue().getTitle())
                .isEqualTo(bookForm.getTitle());
    }

    @Test
    void should_find_book_by_id() {
        // given
        given(bookRepository.findById(anyLong())).willReturn(Optional.of(this.book));

        // when
        Optional<Book> foundBook = bookLoanService.findBookById(1);

        // then
        assertThat(foundBook)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(this.book);
    }

    @Test
    void should_find_all_books() {
        // given
        given(bookRepository.findAll()).willReturn(List.of(this.book));

        // when
        List<Book> foundBooks = bookLoanService.findAllBooks();

        // then
        assertThat(foundBooks)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(List.of(this.book));
    }

    @Test
    void should_delete_book() {
        // given
        willDoNothing().given(bookRepository).deleteById(anyLong());

        // when
        bookLoanService.deleteBook(1L);

        // then
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void should_loan_book_based_on_form_data() {
        // given
        BookLoanedForm bookLoanedForm = new BookLoanedForm();
        bookLoanedForm.setRecipient("user");
        bookLoanedForm.setBookReturnAt(LocalDateTime.now());

        given(bookRepository.findById(1L)).willReturn(Optional.of(this.book));

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        when(bookRepository.save(captor.capture())).thenReturn(this.book);

        // when
        bookLoanService.loanBook(1L, bookLoanedForm);

        // then
        assertThat(captor.getValue().getStatus())
                .isEqualTo(Book.BookStatus.LOANED_OUT);
        assertThat(captor.getValue().getRecipient())
                .isEqualTo(bookLoanedForm.getRecipient());
        assertThat(captor.getValue().getBookReturnAt())
                .isEqualTo(bookLoanedForm.getBookReturnAt());
        assertThat(captor.getValue().getUpdatedAt())
                .isNotNull();
    }

    @Test
    void should_reserve_book_based_on_form_data() {
        // given
        BookReserveForm reserveForm = new BookReserveForm();
        reserveForm.setRecipient("Mike");

        given(bookRepository.findById(1L)).willReturn(Optional.of(this.book));

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        when(bookRepository.save(captor.capture())).thenReturn(this.book);

        // when
        bookLoanService.reserveBook(1L, reserveForm);

        // then
        assertThat(captor.getValue().getStatus())
                .isEqualTo(Book.BookStatus.RESERVED);
        assertThat(captor.getValue().getRecipient())
                .isEqualTo(reserveForm.getRecipient());
        assertThat(captor.getValue())
                .isNotNull();
    }

    @Test
    void should_return_book() {
        // given
        given(bookRepository.findById(1L)).willReturn(Optional.of(this.book));

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        when(bookRepository.save(captor.capture())).thenReturn(this.book);

        // when
        bookLoanService.returnBook(1L);

        // then
        assertThat(captor.getValue().getStatus())
                .isEqualTo(Book.BookStatus.AVAILABLE);
        assertThat(captor.getValue().getRecipient())
                .isNull();
        assertThat(captor.getValue().getBookReturnAt())
                .isNull();
        assertThat(captor.getValue().getUpdatedAt())
                .isNotNull();
    }

    @Test
    void should_mark_book_as_received() {
        // given
        given(bookRepository.findById(1L)).willReturn(Optional.of(this.book));

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        when(bookRepository.save(captor.capture())).thenReturn(this.book);

        // when
        bookLoanService.markAsReceived(1L);

        // then
        assertThat(captor.getValue().getStatus())
                .isEqualTo(Book.BookStatus.RECEIVED);
        assertThat(captor.getValue().getUpdatedAt())
                .isNotNull();
    }
}