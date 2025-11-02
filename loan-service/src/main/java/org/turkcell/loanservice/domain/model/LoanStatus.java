package org.turkcell.loanservice.domain.model;

/**
 * Ödünç durumunu belirten enum
 */
public enum LoanStatus {
    // Kitap hala ödünçte, iade edilmedi
    ACTIVE,

    // Kitap iade edildi
    RETURNED,

    // Süresi geçti, hala iade edilmedi
    OVERDUE
}

