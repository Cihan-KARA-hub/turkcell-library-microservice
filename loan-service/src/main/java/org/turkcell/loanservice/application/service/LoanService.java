package org.turkcell.loanservice.application.service;

import org.turkcell.loanservice.application.dto.LoanRequest;
import org.turkcell.loanservice.application.dto.LoanResponse;
import org.turkcell.loanservice.application.dto.LoanUpdateRequest;
import org.turkcell.loanservice.domain.model.Loan;
import org.turkcell.loanservice.domain.model.LoanStatus;
import org.turkcell.loanservice.domain.repository.LoanRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Loan Service sınıfı
 * Bu sınıf ödünç işlemleriyle ilgili tüm business logic'i içerir
 */
public class LoanService {
    // Veritabanı işlemleri için repository
    private final LoanRepository loanRepository;

    // Constructor - repository'yi enjekte eder
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    /**
     * Yeni bir ödünç kaydı oluşturur
     * @param request Ödünç bilgilerini içeren request objesi
     * @return Oluşturulan ödünç kaydının bilgileri
     */
    public LoanResponse createLoan(LoanRequest request) {
        // Ödünç alma tarihi gönderilmemişse bugünün tarihini kullan
        LocalDate loanDate = request.getLoanDate();
        if (loanDate == null) {
            loanDate = LocalDate.now();
        }

        // Yeni Loan domain objesi oluştur
        Loan loan = new Loan(
                request.getBookId(),
                request.getUserId(),
                loanDate,
                request.getDueDate()
        );

        // Veritabanına kaydet
        Loan savedLoan = loanRepository.save(loan);

        // Response DTO'suna dönüştür ve döndür
        return mapToResponse(savedLoan);
    }

    /**
     * ID'ye göre ödünç kaydı bulur
     * @param id Aranacak ödünç kaydının ID'si
     * @return Bulunan ödünç kaydı
     * @throws RuntimeException Kayıt bulunamazsa hata fırlatır
     */
    public LoanResponse getLoanById(UUID id) {
        // Veritabanından kaydı bul
        var optionalLoan = loanRepository.findById(id);

        // Kayıt yoksa hata fırlat
        if (optionalLoan.isEmpty()) {
            throw new RuntimeException("Loan not found with id: " + id);
        }

        // Kaydı response'a dönüştür ve döndür
        Loan loan = optionalLoan.get();
        return mapToResponse(loan);
    }

    /**
     * Tüm ödünç kayıtlarını getirir
     * @return Tüm ödünç kayıtlarının listesi
     */
    public List<LoanResponse> getAllLoans() {
        // Veritabanından tüm kayıtları al
        List<Loan> allLoans = loanRepository.findAll();

        // Her bir Loan'ı LoanResponse'a dönüştür
        List<LoanResponse> responseList = new ArrayList<>();
        for (Loan loan : allLoans) {
            LoanResponse response = mapToResponse(loan);
            responseList.add(response);
        }

        return responseList;
    }

    /**
     * Belirli bir kullanıcıya ait ödünç kayıtlarını getirir
     * @param userId Kullanıcının ID'si
     * @return Kullanıcıya ait ödünç kayıtlarının listesi
     */
    public List<LoanResponse> getLoansByUserId(UUID userId) {
        // Veritabanından kullanıcıya ait kayıtları al
        List<Loan> userLoans = loanRepository.findByUserId(userId);

        // Her bir Loan'ı LoanResponse'a dönüştür
        List<LoanResponse> responseList = new ArrayList<>();
        for (Loan loan : userLoans) {
            LoanResponse response = mapToResponse(loan);
            responseList.add(response);
        }

        return responseList;
    }

    /**
     * Belirli bir kitaba ait ödünç kayıtlarını getirir
     * @param bookId Kitabın ID'si
     * @return Kitaba ait ödünç kayıtlarının listesi
     */
    public List<LoanResponse> getLoansByBookId(UUID bookId) {
        // Veritabanından kitaba ait kayıtları al
        List<Loan> bookLoans = loanRepository.findByBookId(bookId);

        // Her bir Loan'ı LoanResponse'a dönüştür
        List<LoanResponse> responseList = new ArrayList<>();
        for (Loan loan : bookLoans) {
            LoanResponse response = mapToResponse(loan);
            responseList.add(response);
        }

        return responseList;
    }

    /**
     * Mevcut bir ödünç kaydını günceller
     * @param id Güncellenecek kaydın ID'si
     * @param request Güncellenecek bilgileri içeren request objesi
     * @return Güncellenmiş ödünç kaydının bilgileri
     * @throws RuntimeException Kayıt bulunamazsa hata fırlatır
     */
    public LoanResponse updateLoan(UUID id, LoanUpdateRequest request) {
        // Önce kaydı bul
        var optionalLoan = loanRepository.findById(id);

        // Kayıt yoksa hata fırlat
        if (optionalLoan.isEmpty()) {
            throw new RuntimeException("Loan not found with id: " + id);
        }

        Loan loan = optionalLoan.get();

        // Sadece gönderilen alanları güncelle
        // Kitap ID'si gönderildiyse güncelle
        if (request.getBookId() != null) {
            loan.setBookId(request.getBookId());
        }

        // Kullanıcı ID'si gönderildiyse güncelle
        if (request.getUserId() != null) {
            loan.setUserId(request.getUserId());
        }

        // Ödünç alma tarihi gönderildiyse güncelle
        if (request.getLoanDate() != null) {
            loan.setLoanDate(request.getLoanDate());
        }

        // Geri verme tarihi gönderildiyse güncelle
        if (request.getDueDate() != null) {
            loan.setDueDate(request.getDueDate());
        }

        // İade tarihi gönderildiyse, kitabı iade et
        if (request.getReturnDate() != null) {
            loan.returnLoan(request.getReturnDate());
        }

        // Güncellenmiş kaydı veritabanına kaydet
        Loan updatedLoan = loanRepository.save(loan);

        // Response'a dönüştür ve döndür
        return mapToResponse(updatedLoan);
    }

    /**
     * Bir ödünç kaydını siler
     * @param id Silinecek kaydın ID'si
     * @throws RuntimeException Kayıt bulunamazsa hata fırlatır
     */
    public void deleteLoan(UUID id) {
        // Önce kaydın var olup olmadığını kontrol et
        boolean exists = loanRepository.existsById(id);

        // Kayıt yoksa hata fırlat
        if (!exists) {
            throw new RuntimeException("Loan not found with id: " + id);
        }

        // Kaydı sil
        loanRepository.deleteById(id);
    }

    /**
     * Bir ödünç kaydını iade eder (durumu RETURNED yapar)
     * @param id İade edilecek kaydın ID'si
     * @param returnDate İade tarihi (gönderilmezse bugünün tarihi kullanılır)
     * @return İade edilmiş ödünç kaydının bilgileri
     * @throws RuntimeException Kayıt bulunamazsa hata fırlatır
     */
    public LoanResponse returnLoan(UUID id, LocalDate returnDate) {
        // Önce kaydı bul
        var optionalLoan = loanRepository.findById(id);

        // Kayıt yoksa hata fırlat
        if (optionalLoan.isEmpty()) {
            throw new RuntimeException("Loan not found with id: " + id);
        }

        Loan loan = optionalLoan.get();

        // İade tarihi gönderilmemişse bugünün tarihini kullan
        LocalDate actualReturnDate = returnDate;
        if (actualReturnDate == null) {
            actualReturnDate = LocalDate.now();
        }

        // Kitabı iade et
        loan.returnLoan(actualReturnDate);

        // Güncellenmiş kaydı veritabanına kaydet
        Loan returnedLoan = loanRepository.save(loan);

        // Response'a dönüştür ve döndür
        return mapToResponse(returnedLoan);
    }

    /**
     * Loan domain objesini LoanResponse DTO'suna dönüştürür
     * @param loan Dönüştürülecek Loan objesi
     * @return LoanResponse objesi
     */
    private LoanResponse mapToResponse(Loan loan) {
        // Loan objesindeki tüm bilgileri al
        UUID id = loan.getId();
        UUID bookId = loan.getBookId();
        UUID userId = loan.getUserId();
        LocalDate loanDate = loan.getLoanDate();
        LocalDate dueDate = loan.getDueDate();
        LocalDate returnDate = loan.getReturnDate();
        LoanStatus status = loan.getStatus();
        var penaltyAmount = loan.getPenaltyAmount();


        return new LoanResponse(
                id,
                bookId,
                userId,
                loanDate,
                dueDate,
                returnDate,
                status,
                penaltyAmount
        );
    }
}

