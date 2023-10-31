package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
}
