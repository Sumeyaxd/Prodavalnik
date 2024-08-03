package com.prodavalnik.prodavalnik.service;

import com.prodavalnik.prodavalnik.model.dto.AddCommentDTO;
import com.prodavalnik.prodavalnik.model.dto.CommentsViewDTO;
import com.prodavalnik.prodavalnik.model.entity.Comment;

import java.util.Optional;

public interface CommentService {
    boolean addComment(AddCommentDTO addCommentDTO);

    CommentsViewDTO getAllComments();

    void deleteComment(Long commentId);

    Optional<Comment> findCommentById(Long id);
}
