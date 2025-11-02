package org.turkcell.loanservice.infrastructure.persistence.repository;

import org.springframework.stereotype.Repository;
import org.turkcell.loanservice.domain.model.Loan;
import org.turkcell.loanservice.domain.model.LoanStatus;
import org.turkcell.loanservice.domain.repository.LoanRepository;
import org.turkcell.loanservice.infrastructure.persistence.entity.LoanEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * LoanRepository interface'inin implementasyonu
 * Bu sınıf veritabanı işlemlerini JPA kullanarak yapar
 */
@Repository
public class LoanRepositoryImpl implements LoanRepository {
    // JPA repository - Spring Data JPA'nın sağladığı repository
    private final JpaLoanRepository jpaLoanRepository;

    // Constructor - JPA repository'yi enjekte eder
    public LoanRepositoryImpl(JpaLoanRepository jpaLoanRepository) {
        this.jpaLoanRepository = jpaLoanRepository;
    }

    /**
     * Yeni bir ödünç kaydı oluşturur veya mevcut bir kaydı günceller
     */
    @Override
    public Loan save(Loan loan) {
        // Domain model'i JPA entity'ye dönüştür
        LoanEntity entity = mapToEntity(loan);

        // JPA repository kullanarak veritabanına kaydet
        LoanEntity savedEntity = jpaLoanRepository.save(entity);

        // JPA entity'yi domain model'e dönüştür ve döndür
        return mapToDomain(savedEntity);
    }

    /**
     * ID'ye göre ödünç kaydı bulur
     */
    @Override
    public Optional<Loan> findById(UUID id) {
        // JPA repository'den kaydı bul
        Optional<LoanEntity> optionalEntity = jpaLoanRepository.findById(id);

        // Kayıt yoksa boş Optional döndür
        if (optionalEntity.isEmpty()) {
            return Optional.empty();
        }

        // Kaydı domain model'e dönüştür ve Optional içinde döndür
        LoanEntity entity = optionalEntity.get();
        Loan loan = mapToDomain(entity);
        return Optional.of(loan);
    }

    /**
     * Tüm ödünç kayıtlarını getirir
     */
    @Override
    public List<Loan> findAll() {
        // JPA repository'den tüm kayıtları al
        List<LoanEntity> entityList = jpaLoanRepository.findAll();

        // Her bir entity'yi domain model'e dönüştür
        List<Loan> loanList = new ArrayList<>();
        for (LoanEntity entity : entityList) {
            Loan loan = mapToDomain(entity);
            loanList.add(loan);
        }

        return loanList;
    }

    /**
     * Belirli bir kullanıcıya ait ödünç kayıtlarını getirir
     */
    @Override
    public List<Loan> findByUserId(UUID userId) {
        // JPA repository'den kullanıcıya ait kayıtları al
        List<LoanEntity> entityList = jpaLoanRepository.findByUserId(userId);

        // Her bir entity'yi domain model'e dönüştür
        List<Loan> loanList = new ArrayList<>();
        for (LoanEntity entity : entityList) {
            Loan loan = mapToDomain(entity);
            loanList.add(loan);
        }

        return loanList;
    }

    /**
     * Belirli bir kitaba ait ödünç kayıtlarını getirir
     */
    @Override
    public List<Loan> findByBookId(UUID bookId) {
        // JPA repository'den kitaba ait kayıtları al
        List<LoanEntity> entityList = jpaLoanRepository.findByBookId(bookId);

        // Her bir entity'yi domain model'e dönüştür
        List<Loan> loanList = new ArrayList<>();
        for (LoanEntity entity : entityList) {
            Loan loan = mapToDomain(entity);
            loanList.add(loan);
        }

        return loanList;
    }

    /**
     * ID'ye göre ödünç kaydını siler
     */
    @Override
    public void deleteById(UUID id) {
        // JPA repository kullanarak kaydı sil
        jpaLoanRepository.deleteById(id);
    }

    /**
     * Bu ID'ye sahip bir kayıt var mı kontrol eder
     */
    @Override
    public boolean existsById(UUID id) {
        // JPA repository kullanarak kaydın var olup olmadığını kontrol et
        return jpaLoanRepository.existsById(id);
    }

    /**
     * Domain model (Loan) objesini JPA entity (LoanEntity) objesine dönüştürür
     * Bu işlem veritabanına kaydetmeden önce yapılır
     */
    private LoanEntity mapToEntity(Loan loan) {
        // Yeni bir JPA entity oluştur
        LoanEntity entity = new LoanEntity();

        // Domain model'deki tüm bilgileri entity'ye kopyala
        entity.setId(loan.getId());
        entity.setBookId(loan.getBookId());
        entity.setUserId(loan.getUserId());
        entity.setLoanDate(loan.getLoanDate());
        entity.setDueDate(loan.getDueDate());
        entity.setReturnDate(loan.getReturnDate());
        entity.setStatus(loan.getStatus());
        entity.setPenaltyAmount(loan.getPenaltyAmount());

        return entity;
    }

    /**
     * JPA entity (LoanEntity) objesini domain model (Loan) objesine dönüştürür
     * Bu işlem veritabanından okuma yapıldıktan sonra yapılır
     */
    private Loan mapToDomain(LoanEntity entity) {
        // Yeni bir domain model oluştur
        Loan loan = new Loan();

        // Entity'deki tüm bilgileri domain model'e kopyala
        loan.setId(entity.getId());
        loan.setBookId(entity.getBookId());
        loan.setUserId(entity.getUserId());
        loan.setLoanDate(entity.getLoanDate());
        loan.setDueDate(entity.getDueDate());
        loan.setReturnDate(entity.getReturnDate());
        loan.setStatus(entity.getStatus());
        loan.setPenaltyAmount(entity.getPenaltyAmount());

        return loan;
    }
}

