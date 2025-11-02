package org.turkcell.loanservice.application.dto;

import java.time.LocalDate;
import java.util.UUID;


public class LoanUpdateRequest {
    // Güncellenecek kitap ID'si (opsiyonel)
    private UUID bookId;

    // Güncellenecek kullanıcı ID'si (opsiyonel)
    private UUID userId;

    // Güncellenecek ödünç alma tarihi (opsiyonel)
    private LocalDate loanDate;

    // Güncellenecek geri verme tarihi (opsiyonel)
    private LocalDate dueDate;

    // İade tarihi (opsiyonel - gönderilirse kitap iade edilmiş olur)
    private LocalDate returnDate;

    // Boş constructor
    public LoanUpdateRequest() {
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

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}

