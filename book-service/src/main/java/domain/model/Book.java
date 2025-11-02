package domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Book {
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private Integer publishedYear;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
     public Book(){}
    // Private constructor for creation
    private Book(UUID id, String title, String author, String isbn,
                 Integer publishedYear, String description,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Factory method
    public static Book create(String title, String author, String isbn,
                              Integer publishedYear, String description) {
        LocalDateTime now = LocalDateTime.now();
        return new Book(UUID.randomUUID(), title, author, isbn,
                publishedYear, description, now, now);
    }

    // Business logic
    public void update(String title, String author, String description) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
        }
        if (author != null && !author.isEmpty()) {
            this.author = author;
        }
        if (description != null) {
            this.description = description;
        }
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public Integer getPublishedYear() { return publishedYear; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}