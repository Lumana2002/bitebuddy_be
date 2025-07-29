package com.rajan.foodDeliveryApp.services.impl;

import com.rajan.foodDeliveryApp.config.impl.UserPatcher;
import com.rajan.foodDeliveryApp.domain.entities.PasswordResetTokenEntity;
import com.rajan.foodDeliveryApp.domain.entities.UserEntity;
import com.rajan.foodDeliveryApp.repositories.PasswordResetTokenRepository;
import com.rajan.foodDeliveryApp.repositories.UserRepository;
import com.rajan.foodDeliveryApp.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserPatcher patcher;
    private final PasswordResetTokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserPatcher patcher, PasswordResetTokenRepository tokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.patcher = patcher;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity save(UserEntity user, Long id) {
        user.setId(id);
        UserEntity existingUser = findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        try {
            patcher.patch(existingUser, user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userRepository.save(existingUser);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<UserEntity> findOne(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean isExists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean isExistsByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void createPasswordResetTokenForUser(UserEntity user, String token) {
        if (!tokenRepository.existsByUser(user)) {
            PasswordResetTokenEntity passwordResetToken = new PasswordResetTokenEntity(token, user);
            tokenRepository.save(passwordResetToken);
        } else {
            PasswordResetTokenEntity passwordResetToken = tokenRepository.findByUser(user);
            passwordResetToken.setToken(token);
            tokenRepository.save(passwordResetToken);
        }
    }

    @Transactional
    public void updatePassword(UserEntity user, String newPassword, String token) {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.deleteByToken(token);
    }

    public boolean validatePasswordResetToken(String token) {
        PasswordResetTokenEntity resetToken = tokenRepository.findByToken(token);
        log.info("UserServiceImpl: resetToken: {}", resetToken);
        if (resetToken == null || resetToken.getExpiryDate().before(new Date())) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<UserEntity> findByPasswordResetToken(String token) {
        PasswordResetTokenEntity passwordResetToken = tokenRepository.findByToken(token);
        return passwordResetToken != null ? Optional.of(passwordResetToken.getUser()) : Optional.empty();
    }

}
