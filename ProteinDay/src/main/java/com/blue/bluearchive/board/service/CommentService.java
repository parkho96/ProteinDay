package com.blue.bluearchive.board.service;

import com.blue.bluearchive.board.dto.CommentDto;
import com.blue.bluearchive.board.dto.CommentsCommentDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.board.repository.BoardRepository;
import com.blue.bluearchive.board.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    private final BoardRepository boardRepository;

    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.boardRepository = boardRepository;
    }

    public List<CommentDto> getCommentByBoardId(int boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        if (board == null) {
            // 예외 처리 또는 오류 처리
            return Collections.emptyList(); // 빈 목록 반환하거나 예외 처리 로직 추가
        }

        List<Comment> comments = commentRepository.findByBoardId(board);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(modelMapper.map(comment, CommentDto.class));
        }
        return commentDtos;
    }


}
