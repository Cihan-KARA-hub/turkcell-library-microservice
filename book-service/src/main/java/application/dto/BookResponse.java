package application.dto;

import domain.model.Book;

import java.time.LocalDateTime;

public class BookResponse {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private Integer publishedYear;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BookResponse fromDomain(Book book) {
        BookResponse response = new BookResponse();
        response.id = book.getId().toString();
        response.title = book.getTitle();
        response.author = book.getAuthor();
        response.isbn = book.getIsbn();
        response.publishedYear = book.getPublishedYear();
        response.description = book.getDescription();
        response.createdAt = book.getCreatedAt();
        response.updatedAt = book.getUpdatedAt();
        return response;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
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


