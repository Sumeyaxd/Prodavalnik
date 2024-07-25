package com.example.demo.service.impl;


import com.example.demo.model.dto.AddCommentDTO;
import com.example.demo.model.dto.CommentsDTO;
import com.example.demo.model.dto.CommentsViewDTO;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserService;
import com.example.demo.service.exception.DeleteObjectException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, UserService userService,
                              ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean addComment(AddCommentDTO addCommentDTO) {

        if (addCommentDTO == null) {
            return false;
        }

        String username = this.userService.getLoggedUsername();

        Optional<User> optionalUser = this.userService.findUserByUsername(username);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();

        Comment comment = this.modelMapper.map(addCommentDTO, Comment.class);
        comment.setAddedOn(LocalDateTime.now());
        comment.setUser(user);
        user.getComments().add(comment);

        this.commentRepository.saveAndFlush(comment);
        this.userService.saveAndFlushUser(user);
        return true;
    }

    @Override
    public void deleteComment(Long id) {

        String username = this.userService.getLoggedUsername();

        Optional<Comment> optionalComment = this.commentRepository.findById(id);
        Optional<User> optionalUser = this.userService.findUserByUsername(username);

        if (optionalComment.isPresent() && optionalUser.isPresent()) {

            Comment comment = optionalComment.get();

            if (comment.getUser().getUsername().equals(username)) {
                this.commentRepository.deleteById(id);
            }
        } else {
            throw new DeleteObjectException("You cannot delete comment with id " + id + "!");
        }
    }

    @Override
    public CommentsViewDTO getAllComments() {

        List<Comment> comments = this.commentRepository.findAll();

        List<CommentsDTO> commentDetailsDTO = comments.stream()
                .map(comment -> {
                    CommentsDTO dto = this.modelMapper.map(comment, CommentsDTO.class);
                    dto.setFullName(comment.getUser().getFullName());
                    dto.setAddedOn(comment.parseDateToString(comment.getAddedOn()));

                    return dto;
                }).toList();

        return new CommentsViewDTO(commentDetailsDTO);
    }

}