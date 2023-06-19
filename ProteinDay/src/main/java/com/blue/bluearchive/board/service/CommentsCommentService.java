package com.blue.bluearchive.board.service;

import com.blue.bluearchive.board.dto.CommentDto;
import com.blue.bluearchive.board.dto.CommentsCommentDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.board.repository.CommentRepository;
import com.blue.bluearchive.board.repository.CommentsCommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommentsCommentService {
    private final CommentRepository commentRepository;

    private final CommentsCommentRepository commentsCommentRepository;

    private final ModelMapper modelMapper;

    public CommentsCommentService(CommentRepository commentRepository, CommentsCommentRepository commentsCommentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.commentsCommentRepository = commentsCommentRepository;
        this.modelMapper = modelMapper;
    }
    public List<CommentsCommentDto> getCommentsCommentByCommentId(int commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (comment == null) {
            // 예외 처리 또는 오류 처리
            return Collections.emptyList(); // 빈 목록 반환하거나 예외 처리 로직 추가
        }
        List<CommentsComment> commentsComments = commentsCommentRepository.findByComment(comment);
        List<CommentsCommentDto> commentsCommentDtos = new ArrayList<>();
        for(CommentsComment commentsComment : commentsComments){
            commentsCommentDtos.add(modelMapper.map(commentsComment, CommentsCommentDto.class));
        }
        return commentsCommentDtos;
    }


}
