package com.baylor.diabeticselfed.repository;

import com.baylor.diabeticselfed.entities.Comment;
import com.baylor.diabeticselfed.entities.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
    List<ForumPost> findByPatient_Id(Long patientId);
    boolean existsByIdAndPatient_Id(Long postId, Long patientId);
    List<ForumPost> findByTitleContainingOrContentContaining(String title, String content);
    @Query("SELECT fp FROM ForumPost fp WHERE LOWER(fp.title) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR LOWER(fp.content) LIKE LOWER(CONCAT('%', :searchValue, '%'))")
    List<ForumPost> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String searchValue);

}
