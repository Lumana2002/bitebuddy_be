package com.rajan.foodDeliveryApp.services;

import com.rajan.foodDeliveryApp.domain.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    UserEntity save(UserEntity user);

    UserEntity save(UserEntity user, Long id);

    Page<UserEntity> findAll(Pageable pageable);

    Optional<UserEntity> findOne(Long id);

    Optional<UserEntity> findByEmail(String email);

    boolean isExists(Long id);

    boolean isExistsByEmail(String email);

    void delete(Long id);

    void createPasswordResetTokenForUser(UserEntity user, String token);

    void updatePassword(UserEntity user, String newPassword, String token);

    boolean validatePasswordResetToken(String token);

    Optional<UserEntity> findByPasswordResetToken(String token);
}
