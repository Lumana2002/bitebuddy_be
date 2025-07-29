package com.rajan.foodDeliveryApp.repositories;

import com.rajan.foodDeliveryApp.domain.entities.PasswordResetTokenEntity;
import com.rajan.foodDeliveryApp.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {
    PasswordResetTokenEntity findByToken(String token);

    PasswordResetTokenEntity findByUser(UserEntity user);

    void deleteByToken(String token);

    boolean existsByUser(UserEntity user);
}
