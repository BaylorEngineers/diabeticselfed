package com.baylor.diabeticselfed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.baylor.diabeticselfed.entities.Invitation;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Invitation findByToken(String token);
}
