package org.tapalka.repository;

import org.tapalka.model.Referral;
import org.tapalka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferralRepository extends JpaRepository<Referral, Long> {
    List<Referral> findByInviter(User inviter);
}
