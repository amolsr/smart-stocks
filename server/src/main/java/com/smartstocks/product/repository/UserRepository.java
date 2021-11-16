package com.smartstocks.product.repository;

import com.smartstocks.product.models.StockTransactions;
import com.smartstocks.product.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT t FROM User u JOIN u.transactions t WHERE t.type <> 'CREDIT' AND u.email = :email ORDER BY t.transactionsDate DESC")
    Set<StockTransactions> nonCreditTransactions(String email);

    @Query("SELECT t FROM User u JOIN u.transactions t WHERE t.type = 'CREDIT' AND u.email = :email ORDER BY t.transactionsDate DESC")
    Set<StockTransactions> creditTransactions(String email);
}
