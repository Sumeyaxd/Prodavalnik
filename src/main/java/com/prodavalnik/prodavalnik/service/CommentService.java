package com.prodavalnik.prodavalnik.service;

import com.prodavalnik.prodavalnik.model.dto.AddCommentDTO;
import com.prodavalnik.prodavalnik.model.dto.CommentsViewDTO;

public interface CommentService {
    boolean addComment(AddCommentDTO addCommentDTO);

    CommentsViewDTO getAllComments();

    void deleteComment(Long commentId);
}
