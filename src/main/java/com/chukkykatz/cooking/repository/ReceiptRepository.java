package com.chukkykatz.cooking.repository;

import com.chukkykatz.cooking.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
}
