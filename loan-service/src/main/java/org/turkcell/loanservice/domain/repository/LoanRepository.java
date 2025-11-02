package org.turkcell.loanservice.domain.repository;

import org.turkcell.loanservice.domain.model.Loan;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Loan repository interface
 * Bu interface veritabanı işlemlerini tanımlar
 */
public interface LoanRepository {

    // Yeni bir ödünç kaydı oluştur veya mevcut bir kaydı güncelle
    Loan save(Loan loan);

    // ID'ye göre ödünç kaydı bul (bulunamazsa null döner)
    Optional<Loan> findById(UUID id);

    // Tüm ödünç kayıtlarını getir
    List<Loan> findAll();

    // Belirli bir kullanıcıya ait tüm ödünç kayıtlarını getir
    List<Loan> findByUserId(UUID userId);

    // Belirli bir kitaba ait tüm ödünç kayıtlarını getir
    List<Loan> findByBookId(UUID bookId);

    // ID'ye göre ödünç kaydını sil
    void deleteById(UUID id);

    // Bu ID'ye sahip bir kayıt var mı kontrol et
    boolean existsById(UUID id);
}

