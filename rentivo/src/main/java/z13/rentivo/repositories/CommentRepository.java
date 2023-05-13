package z13.rentivo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Comment;

@Transactional @Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query
    List<Comment> findByCommentId(Long commentId);

    @Query
    List<Comment> findByContent(String content);

    @Modifying
    @Query(value = "INSERT INTO comment (comment_id, content, rental_id) VALUES (:comment_id, :content, :rental_id)", nativeQuery = true)
    void insertComment(@Param("comment_id") Long commentId, 
                       @Param("content") String content, 
                       @Param("rental_id") Long rentalId);
}