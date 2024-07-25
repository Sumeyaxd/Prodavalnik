package com.example.demo.model.dto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CommentsViewDTO {
    List<CommentsDTO> comments;

    private String totalRating;

    public CommentsViewDTO() {
        this.comments = new ArrayList<>();
    }

    public CommentsViewDTO(List<CommentsDTO> comments) {
        this.comments = comments;
    }

    public List<CommentsDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentsDTO> comments) {
        this.comments = comments;
    }

    public String getTotalRating() {
        Integer totalRating = this.comments.stream()
                .map(CommentsDTO::getRating)
                .reduce(0, Integer::sum);

        DecimalFormat df = new DecimalFormat("##.00");
        String result = df.format((double) totalRating / this.comments.size());

        return (this.comments.size() == 0) ? "0" : result;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }
}
