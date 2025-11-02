package infrastructure.persistence;


import infrastructure.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface JpaBookRepository extends JpaRepository<BookEntity, UUID> {
    Optional<BookEntity> findByIsbn(String isbn);
}
