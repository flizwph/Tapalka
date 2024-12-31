package org.tapalka.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tapalka.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByTelegramId(String telegramId);
}
