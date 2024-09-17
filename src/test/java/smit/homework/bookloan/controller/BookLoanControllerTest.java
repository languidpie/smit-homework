package smit.homework.bookloan.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import smit.homework.bookloan.controller.forms.BookForm;
import smit.homework.bookloan.controller.forms.BookLoanedForm;
import smit.homework.bookloan.controller.forms.BookReserveForm;
import smit.homework.bookloan.entity.Book;
import smit.homework.bookloan.service.BookLoanService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link BookLoanController} class.
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookLoanControllerTest {

    @MockBean
    private BookLoanService bookLoanService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext).apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void should_find_all_books() throws Exception {
        // given
        Book firstBook = Book.builder()
                .title("Tõde ja Õigus")
                .author("A. H. Tammsaare")
                .status(Book.BookStatus.AVAILABLE)
                .build();

        Book secondBook = Book.builder()
                .title("Anna Karenina")
                .author("Lev Tolstoy")
                .status(Book.BookStatus.AVAILABLE)
                .build();

        when(bookLoanService.findAllBooks()).thenReturn(List.of(firstBook, secondBook));

        // when
        this.mockMvc.perform(get("/api/books"))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(firstBook.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(firstBook.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value(firstBook.getStatus().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value(secondBook.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value(secondBook.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].status").value(secondBook.getStatus().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void should_find_once_distinct_book() throws Exception {
        // given
        Book book = Book.builder()
                .id(1)
                .title("1984")
                .author("George Orwell")
                .status(Book.BookStatus.RESERVED)
                .build();

        when(bookLoanService.findBookById(anyLong())).thenReturn(Optional.of(book));

        // when
        this.mockMvc.perform(get("/api/books/" + book.getId()))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(book.getStatus().toString()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void should_add_new_book() throws Exception {
        // given
        Book newBook = Book.builder()
                .id(1)
                .title("Jane Eyre")
                .author("Charlotte Brontë")
                .publisher("Smith, Elder & Co.")
                .isbn("978-3-16-148410-0")
                .year(1847)
                .genre("Romance")
                .build();

        BookForm bookForm = new BookForm();
        bookForm.setTitle(newBook.getTitle());
        bookForm.setAuthor(newBook.getAuthor());

        when(bookLoanService.saveNewBook(any(BookForm.class))).thenReturn(newBook);

        // when
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookForm)))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(newBook.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(newBook.getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher").value(newBook.getPublisher()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(newBook.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(newBook.getYear()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(newBook.getGenre()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void should_update_book() throws Exception {
        // given
        Book bookToBeChanged = Book.builder()
                .id(22)
                .title("Lord of the Rings")
                .author("J.R.R. Tolkien")
                .build();

        when(bookLoanService.updateBook(anyLong(), any(BookForm.class))).thenReturn(bookToBeChanged);

        // when
        this.mockMvc.perform(put("/api/books/" + bookToBeChanged.getId()  + "/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new BookForm())))
                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookToBeChanged.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookToBeChanged.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(bookToBeChanged.getAuthor()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void should_reserve_book() throws Exception {
        // given
        BookReserveForm reserveForm = new BookReserveForm();
        reserveForm.setRecipient("user");

        // when
        this.mockMvc.perform(put("/api/books/1/reserve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(reserveForm)))
                // then
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void should_loan_out_book() throws Exception {
        // given
        BookLoanedForm loanedForm = new BookLoanedForm();
        loanedForm.setBookReturnAt(LocalDateTime.now());
        loanedForm.setRecipient("user");

        // when
        this.mockMvc.perform(put("/api/books/1/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loanedForm)))
                // then
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void should_set_book_as_returned() throws Exception {
        // given

        // then
        this.mockMvc.perform(put("/api/books/1/return"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void should_set_book_as_received() throws Exception {
        // given

        // then
        this.mockMvc.perform(put("/api/books/1/received"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void should_delete_book() throws Exception {
        // given

        // then
        this.mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
    }

}