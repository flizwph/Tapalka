// User.java
package org.tapalka.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String telegramId; // Уникальный Telegram ID

    private String username;
    private String firstName;
    private String lastName;
    private String photoUrl;

    private int stars;  // Звёзды, уже имеющиеся у пользователя (по логике приложения)
    private int coins;  // Монеты
    private int score;
    private int level;

    // Новый поля для буста
    private double boostMultiplier = 1.0; // По умолчанию без буста

    @OneToMany(mappedBy = "inviter", cascade = CascadeType.ALL)
    private List<Referral> referrals;
}
