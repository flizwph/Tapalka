package org.tapalka.service;

import org.tapalka.model.User;
import org.tapalka.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;

@Service
public class AuthService {
    private final UserRepository userRepository;
    // Подставьте ваш реальный токен бота
    private static final String BOT_TOKEN = "Ваш_Telegram_Bot_Token";

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean verifySignature(Map<String, String> data, String hash) {
        try {
            String dataCheckString = data.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .filter(entry -> !"hash".equals(entry.getKey()))
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .reduce((a, b) -> a + "\n" + b)
                    .orElse("");

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(BOT_TOKEN.getBytes(), "HmacSHA256"));

            byte[] computedHash = mac.doFinal(dataCheckString.getBytes());
            String encodedHash = Base64.getEncoder().encodeToString(computedHash);

            return hash.equals(encodedHash);
        } catch (Exception e) {
            return false;
        }
    }

    public User authorizeUser(Map<String, String> userData) {
        String telegramId = userData.get("id");
        String username = userData.get("username");
        String firstName = userData.get("first_name");
        String lastName = userData.get("last_name");
        String photoUrl = userData.get("photo_url");

        User user = userRepository.findByTelegramId(telegramId);
        if (user == null) {
            user = new User();
            user.setTelegramId(telegramId);
            user.setUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhotoUrl(photoUrl);
            user.setStars(0);
            user.setCoins(0);          // Инициализация монет
            user.setScore(0);          // Инициализация очков
            user.setLevel(1);          // Начальный уровень, например 1
            user.setBoostMultiplier(1.0); // Без буста по умолчанию

            userRepository.save(user);
        } else {
            // Обновим информацию о пользователе, если что-то изменилось
            if (username != null && !username.equals(user.getUsername())) {
                user.setUsername(username);
            }
            if (firstName != null && !firstName.equals(user.getFirstName())) {
                user.setFirstName(firstName);
            }
            if (lastName != null && !lastName.equals(user.getLastName())) {
                user.setLastName(lastName);
            }
            if (photoUrl != null && !photoUrl.equals(user.getPhotoUrl())) {
                user.setPhotoUrl(photoUrl);
            }
            userRepository.save(user);
        }

        return user;
    }
}
