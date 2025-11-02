package org.turkcell.loanservice.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.turkcell.loanservice.application.dto.LoanRequest;
import org.turkcell.loanservice.application.dto.LoanResponse;
import org.turkcell.loanservice.application.dto.LoanUpdateRequest;
import org.turkcell.loanservice.application.service.LoanService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Loan Controller sınıfı
 * Bu sınıf HTTP isteklerini karşılar ve LoanService'e yönlendirir
 * REST API endpoint'lerini tanımlar
 */
@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {
    // Business logic işlemleri için service sınıfı
    private final LoanService loanService;

    // Constructor - service'i enjekte eder
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Yeni bir ödünç kaydı oluşturur
     * POST /api/v1/loans
     * @param request Ödünç bilgilerini içeren request body
     * @return Oluşturulan ödünç kaydı (HTTP 201 Created)
     */
    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@RequestBody LoanRequest request) {
        // Service'i kullanarak yeni ödünç kaydı oluştur
        LoanResponse response = loanService.createLoan(request);

        // HTTP 201 Created status kodu ile döndür
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * ID'ye göre ödünç kaydı getirir
     * GET /api/v1/loans/{id}
     * @param id Aranacak ödünç kaydının ID'si
     * @return Bulunan ödünç kaydı (HTTP 200 OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getLoanById(@PathVariable UUID id) {
        // Service'i kullanarak kaydı bul
        LoanResponse response = loanService.getLoanById(id);

        // HTTP 200 OK status kodu ile döndür
        return ResponseEntity.ok(response);
    }

    /**
     * Tüm ödünç kayıtlarını getirir
     * GET /api/v1/loans
     * @return Tüm ödünç kayıtlarının listesi (HTTP 200 OK)
     */
    @GetMapping
    public ResponseEntity<List<LoanResponse>> getAllLoans() {
        // Service'i kullanarak tüm kayıtları getir
        List<LoanResponse> responses = loanService.getAllLoans();

        // HTTP 200 OK status kodu ile döndür
        return ResponseEntity.ok(responses);
    }

    /**
     * Belirli bir kullanıcıya ait ödünç kayıtlarını getirir
     * GET /api/v1/loans/user/{userId}
     * @param userId Kullanıcının ID'si
     * @return Kullanıcıya ait ödünç kayıtlarının listesi (HTTP 200 OK)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanResponse>> getLoansByUserId(@PathVariable UUID userId) {
        // Service'i kullanarak kullanıcıya ait kayıtları getir
        List<LoanResponse> responses = loanService.getLoansByUserId(userId);

        // HTTP 200 OK status kodu ile döndür
        return ResponseEntity.ok(responses);
    }

    /**
     * Belirli bir kitaba ait ödünç kayıtlarını getirir
     * GET /api/v1/loans/book/{bookId}
     * @param bookId Kitabın ID'si
     * @return Kitaba ait ödünç kayıtlarının listesi (HTTP 200 OK)
     */
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<LoanResponse>> getLoansByBookId(@PathVariable UUID bookId) {
        // Service'i kullanarak kitaba ait kayıtları getir
        List<LoanResponse> responses = loanService.getLoansByBookId(bookId);

        // HTTP 200 OK status kodu ile döndür
        return ResponseEntity.ok(responses);
    }

    /**
     * Mevcut bir ödünç kaydını günceller
     * PUT /api/v1/loans/{id}
     * @param id Güncellenecek kaydın ID'si
     * @param request Güncellenecek bilgileri içeren request body
     * @return Güncellenmiş ödünç kaydı (HTTP 200 OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<LoanResponse> updateLoan(@PathVariable UUID id,
                                                   @RequestBody LoanUpdateRequest request) {
        // Service'i kullanarak kaydı güncelle
        LoanResponse response = loanService.updateLoan(id, request);

        // HTTP 200 OK status kodu ile döndür
        return ResponseEntity.ok(response);
    }

    /**
     * Bir ödünç kaydını siler
     * DELETE /api/v1/loans/{id}
     * @param id Silinecek kaydın ID'si
     * @return Boş response (HTTP 204 No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable UUID id) {
        // Service'i kullanarak kaydı sil
        loanService.deleteLoan(id);

        // HTTP 204 No Content status kodu ile döndür
        return ResponseEntity.noContent().build();
    }

    /**
     * Bir ödünç kaydını iade eder
     * POST /api/v1/loans/{id}/return
     * @param id İade edilecek kaydın ID'si
     * @param returnDate İade tarihi (opsiyonel - gönderilmezse bugünün tarihi kullanılır)
     * @return İade edilmiş ödünç kaydı (HTTP 200 OK)
     */
    @PostMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnLoan(@PathVariable UUID id,
                                                   @RequestParam(required = false) LocalDate returnDate) {
        // Service'i kullanarak kitabı iade et
        LoanResponse response = loanService.returnLoan(id, returnDate);

        // HTTP 200 OK status kodu ile döndür
        return ResponseEntity.ok(response);
    }
}

