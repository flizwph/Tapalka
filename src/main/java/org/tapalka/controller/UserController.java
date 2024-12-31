package org.tapalka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tapalka.model.User;
import org.tapalka.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/{telegramId}/tap")
    public ResponseEntity<?> handleTap(@PathVariable String telegramId) {
        User user = userRepository.findByTelegramId(telegramId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Начисление монет и очков с учётом boostMultiplier
        double boostMultiplier = user.getBoostMultiplier() > 0 ? user.getBoostMultiplier() : 1.0;
        // Округляем результат до целого числа, если нужно
        int addedCoins = (int)Math.floor(1 * boostMultiplier);
        user.setCoins(user.getCoins() + addedCoins);
        user.setScore(user.getScore() + 1);

        // Проверка уровня
        int pointsToNextLevel = user.getLevel() == 0 ? 10 : user.getLevel() * 10;
        if (user.getScore() >= pointsToNextLevel) {
            user.setScore(0);
            user.setLevel(user.getLevel() + 1);
        }

        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("level", user.getLevel());
        response.put("score", user.getScore());
        response.put("coins", user.getCoins());
        response.put("stars", user.getStars());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{telegramId}")
    public ResponseEntity<?> getUserData(@PathVariable String telegramId) {
        User user = userRepository.findByTelegramId(telegramId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("level", user.getLevel());
        response.put("score", user.getScore());
        response.put("coins", user.getCoins());
        response.put("stars", user.getStars());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{telegramId}/buyBoost")
    public ResponseEntity<?> buyBoost(@PathVariable String telegramId, @RequestParam int stars) {
        User user = userRepository.findByTelegramId(telegramId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok("Инвойс на оплату отправлен в Telegram!");
    }
}
