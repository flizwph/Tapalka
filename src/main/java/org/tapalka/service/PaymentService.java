// PaymentService.java
package org.tapalka.service;

import org.springframework.stereotype.Service;
import org.tapalka.model.User;

@Service
public class PaymentService {

    public void applyBoost(User user, int stars) {
        // Здесь определяем логику множителей
        double multiplier;
        switch (stars) {
            case 10:
                multiplier = 1.2;
                break;
            case 25:
                multiplier = 1.5;
                break;
            case 50:
                multiplier = 2.0;
                break;
            case 250:
                multiplier = 2.5;
                break;
            case 1000:
                multiplier = 3.0;
                break;
            default:
                multiplier = 1.0;
        }

        user.setBoostMultiplier(multiplier);
        // Если хотите ограничить время действия, можно тут же сохранить время окончания
        // user.setBoostEndTime(LocalDateTime.now().plusDays(1)); // пример
    }
}
