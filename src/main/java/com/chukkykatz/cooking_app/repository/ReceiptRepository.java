package com.chukkykatz.cooking_app.repository;

import com.chukkykatz.cooking_app.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
}
