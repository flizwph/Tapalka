package org.tapalka.service;

import org.tapalka.model.User;
import org.tapalka.model.Referral;
import org.tapalka.repository.UserRepository;
import org.tapalka.repository.ReferralRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReferralService {
    private final UserRepository userRepository;
    private final ReferralRepository referralRepository;

    public ReferralService(UserRepository userRepository, ReferralRepository referralRepository) {
        this.userRepository = userRepository;
        this.referralRepository = referralRepository;
    }

    public String generateReferralLink(String telegramId) {
        return "http://localhost:3000/register?ref=" + telegramId;
    }

    public void handleReferral(String inviterTelegramId, String referralTelegramId) {
        User inviter = userRepository.findByTelegramId(inviterTelegramId);
        if (inviter == null) throw new IllegalArgumentException("Inviter not found");

        User referral = userRepository.findByTelegramId(referralTelegramId);
        if (referral == null) {
            referral = new User();
            referral.setTelegramId(referralTelegramId);
            referral.setCoins(0);
            userRepository.save(referral);
        }

        Referral ref = new Referral();
        ref.setInviter(inviter);
        ref.setReferral(referral);
        referralRepository.save(ref);

        inviter.setCoins(inviter.getCoins() + 100); // Начисление 100 монет
        userRepository.save(inviter);
    }

    public List<Referral> getReferralsWithCoins(String inviterTelegramId) {
        User inviter = userRepository.findByTelegramId(inviterTelegramId);
        if (inviter == null) throw new IllegalArgumentException("Inviter not found");

        return referralRepository.findByInviter(inviter);
    }
}
