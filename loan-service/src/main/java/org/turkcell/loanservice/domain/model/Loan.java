package org.turkcell.loanservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Loan domain model sınıfı
 * Bu sınıf ödünç alınan kitapların bilgilerini tutar
 */
public class Loan {
    // Ödünç kaydının benzersiz kimliği
    private UUID id;

    // Ödünç alınan kitabın ID'si
    private UUID bookId;

    // Ödünç alan kullanıcının ID'si
    private UUID userId;

    // Ödünç alma tarihi
    private LocalDate loanDate;

    // Geri verme tarihi (son gün)
    private LocalDate dueDate;

    // Gerçekte iade edildiği tarih (null olabilir)
    private LocalDate returnDate;

    // Ödünç durumu (AKTİF, İADE EDİLDİ, SÜRESİ GEÇTİ)
    private LoanStatus status;

    // Ceza tutarı (geç iade durumunda)
    private BigDecimal penaltyAmount;


    public Loan() {
    }

    /**
     * Yeni bir ödünç kaydı oluşturur
     * @param bookId Kitap ID'si
     * @param userId Kullanıcı ID'si
     * @param loanDate Ödünç alma tarihi
     * @param dueDate Geri verme tarihi
     */
    public Loan(UUID bookId, UUID userId, LocalDate loanDate, LocalDate dueDate) {
        // Yeni bir benzersiz ID oluştur
        this.id = UUID.randomUUID();
        this.bookId = bookId;
        this.userId = userId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        // Yeni ödünç her zaman aktif durumda başlar
        this.status = LoanStatus.ACTIVE;
        // Başlangıçta ceza tutarı sıfırdır
        this.penaltyAmount = BigDecimal.ZERO;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    /**
     * Ödünç alınan kitabı iade et
     * @param returnDate İade edilme tarihi
     */
    public void returnLoan(LocalDate returnDate) {
        // İade tarihini kaydet
        this.returnDate = returnDate;
        // Durumu "İADE EDİLDİ" olarak güncelle
        this.status = LoanStatus.RETURNED;
    }
}

