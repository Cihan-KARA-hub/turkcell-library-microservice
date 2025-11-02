package infrastructure.persistence.entity;

import jakarta.persistence.*;
import domain.model.Book;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private Integer publishedYear;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Convert Domain to Entity
    public static BookEntity fromDomain(Book book) {
        BookEntity entity = new BookEntity();
        entity.id = book.getId();
        entity.title = book.getTitle();
        entity.author = book.getAuthor();
        entity.isbn = book.getIsbn();
        entity.publishedYear = book.getPublishedYear();
        entity.description = book.getDescription();
        entity.createdAt = book.getCreatedAt();
        entity.updatedAt = book.getUpdatedAt();
        return entity;
    }

    // Convert Entity to Domain
    public Book toDomain() {
        return Book.create(title, author, isbn, publishedYear, description);
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getPublishedYear() { return publishedYear; }
    public void setPublishedYear(Integer publishedYear) { this.publishedYear = publishedYear; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}