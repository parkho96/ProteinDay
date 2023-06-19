package com.blue.bluearchive.board.entity;

import com.blue.bluearchive.shop.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comments_comment")
public class CommentsComment extends BaseEntity {

    @Id
    @Column(name = "comments_comment_id")
    private int commentsCommentId;

    @Column(name = "comments_comment_content", nullable = false)
    private String commentsCommentContent;

    @Column(name = "comments_comment_like_count")
    private Integer commentsCommentLikeCount;

    @Column(name = "comments_comment_hate_count")
    private Integer commentsCommentHateCount;

    @Column(name = "comments_comment_reports_count")
    private Integer commentsCommentReportsCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    private Comment comment;

}