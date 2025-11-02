package infrastructure.persistence;


import domain.model.Book;
import domain.repository.BookRepository;
import infrastructure.persistence.entity.BookEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final JpaBookRepository jpaRepository;

    public BookRepositoryImpl(JpaBookRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Book save(Book book) {
        BookEntity entity = BookEntity.fromDomain(book);
        BookEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(BookEntity::toDomain);
    }

    @Override
    public List<Book> findAll() {
        return jpaRepository.findAll().stream()
                .map(BookEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Book update(Book book) {
        BookEntity entity = BookEntity.fromDomain(book);
        BookEntity updated = jpaRepository.save(entity);
        return updated.toDomain();
    }

    @Override
    public boolean delete(UUID id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return jpaRepository.findByIsbn(isbn)
                .map(BookEntity::toDomain);
    }
}
