package com.chukkykatz.cooking.repository;

import com.chukkykatz.cooking.domain.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<Recipient, Integer> {
}
