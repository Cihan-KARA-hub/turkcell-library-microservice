package application.service;


import application.dto.BookResponse;
import application.dto.CreateBookRequest;
import application.dto.UpdateBookRequest;
import domain.model.Book;
import domain.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse createBook(CreateBookRequest request) {
        // Business rule: ISBN must be unique
        bookRepository.findByIsbn(request.getIsbn())
                .ifPresent(book -> {
                    throw new IllegalArgumentException("Book with ISBN " + request.getIsbn() + " already exists");
                });

        Book book = Book.create(
                request.getTitle(),
                request.getAuthor(),
                request.getIsbn(),
                request.getPublishedYear(),
                request.getDescription()
        );

        Book savedBook = bookRepository.save(book);
        return BookResponse.fromDomain(savedBook);
    }

    @Transactional(readOnly = true)
    public BookResponse getBook(String id) {
        Book book = bookRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + id + " not found"));
        return BookResponse.fromDomain(book);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookResponse::fromDomain)
                .collect(Collectors.toList());
    }

    public BookResponse updateBook(String id, UpdateBookRequest request) {
        Book book = bookRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + id + " not found"));

        book.update(request.getTitle(), request.getAuthor(), request.getDescription());

        Book updatedBook = bookRepository.update(book);
        return BookResponse.fromDomain(updatedBook);
    }

    public void deleteBook(String id) {
        UUID bookId = UUID.fromString(id);
        boolean deleted = bookRepository.delete(bookId);
        if (!deleted) {
            throw new IllegalArgumentException("Book with id " + id + " not found");
        }
    }
}
