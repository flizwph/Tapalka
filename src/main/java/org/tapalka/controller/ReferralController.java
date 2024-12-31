package org.tapalka.controller;

import org.tapalka.model.Referral;
import org.tapalka.service.ReferralService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/referrals")
public class ReferralController {
    private final ReferralService referralService;

    public ReferralController(ReferralService referralService) {
        this.referralService = referralService;
    }

    @GetMapping("/link")
    public String generateReferralLink(@RequestParam String telegramId) {
        return referralService.generateReferralLink(telegramId);
    }

    @PostMapping("/handle")
    public void handleReferral(@RequestParam String inviterId, @RequestParam String referralId) {
        referralService.handleReferral(inviterId, referralId);
    }

    @GetMapping("/stats")
    public List<Referral> getReferralStats(@RequestParam String telegramId) {
        return referralService.getReferralsWithCoins(telegramId);
    }
}
