package com.chukkykatz.cooking_app.repository;

import com.chukkykatz.cooking_app.domain.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipientRepository extends JpaRepository<Recipient, Integer> {
}
