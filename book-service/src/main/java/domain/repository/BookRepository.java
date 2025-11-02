package domain.repository;
import domain.model.Book;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(UUID id);
    List<Book> findAll();
    Book update(Book book);
    boolean delete(UUID id);
    Optional<Book> findByIsbn(String isbn);
}