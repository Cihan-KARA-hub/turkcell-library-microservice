package org.turkcell.loanservice.application.dto;

import java.time.LocalDate;
import java.util.UUID;


public class LoanRequest {
    // Ödünç alınacak kitabın ID'si
    private UUID bookId;

    // Ödünç alan kullanıcının ID'si
    private UUID userId;


    private LocalDate loanDate;

    // Geri verme tarihi (zorunlu)
    private LocalDate dueDate;

    // Boş constructor - Spring Framework için gerekli
    public LoanRequest() {
    }

    // Tüm alanları içeren constructor
    public LoanRequest(UUID bookId, UUID userId, LocalDate loanDate, LocalDate dueDate) {
        this.bookId = bookId;
        this.userId = userId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}

