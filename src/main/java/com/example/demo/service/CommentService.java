package com.example.demo.service;

import com.example.demo.model.dto.AddCommentDTO;
import com.example.demo.model.dto.CommentsViewDTO;

public interface CommentService {
    boolean addComment(AddCommentDTO addCommentDTO);

    CommentsViewDTO getAllComments();

    void deleteComment(Long commentId);
}
