package com.prodavalnik.prodavalnik.repository;

import com.prodavalnik.prodavalnik.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}