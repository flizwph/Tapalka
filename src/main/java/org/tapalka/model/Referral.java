package org.tapalka.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Referral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inviter_id")
    private User inviter;

    @ManyToOne
    @JoinColumn(name = "referral_id")
    private User referral;

    private int referralCoins;
}
