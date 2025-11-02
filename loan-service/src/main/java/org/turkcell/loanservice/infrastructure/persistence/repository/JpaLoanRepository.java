package org.turkcell.loanservice.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.turkcell.loanservice.infrastructure.persistence.entity.LoanEntity;

import java.util.List;
import java.util.UUID;

public interface JpaLoanRepository extends JpaRepository<LoanEntity, UUID> {
    List<LoanEntity> findByUserId(UUID userId);
    List<LoanEntity> findByBookId(UUID bookId);
}

