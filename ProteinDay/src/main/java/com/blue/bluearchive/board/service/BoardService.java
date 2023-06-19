package com.blue.bluearchive.board.service;

import com.blue.bluearchive.board.dto.BoardDto;
import com.blue.bluearchive.board.dto.BoardFormDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Category;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.repository.BoardRepository;
import com.blue.bluearchive.board.repository.CommentRepository;
import com.blue.bluearchive.board.repository.CommentsCommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    private final CommentRepository commentRepository;
    private final CommentsCommentRepository commentsCommentRepository;




    @Autowired
    public BoardService(BoardRepository boardRepository, ModelMapper modelMapper, CommentRepository commentRepository, CommentsCommentRepository commentsCommentRepository) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.commentsCommentRepository = commentsCommentRepository;
    }
    public List<BoardDto> getAllBoards() {
        List<Board> boardEntities = boardRepository.findAll();
        List<BoardDto> BoardDtos = new ArrayList<>();
        for (Board board : boardEntities) {
            BoardDtos.add(modelMapper.map(board, BoardDto.class));
        }
        return BoardDtos;
    }

    public List<BoardDto> getBoardsByCategory(Category category) {
        List<Board> boardEntities = boardRepository.findByCategory(category);
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardEntities) {
            boardDtos.add(modelMapper.map(board, BoardDto.class));
        }
        return boardDtos;
    }
    public BoardDto getBoardById(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));
        return modelMapper.map(board, BoardDto.class);
    }

        public void updateBoard(BoardDto boardDto) {
            // 엔티티를 업데이트하는 로직을 추가합니다.
            Board board = boardRepository.findById(boardDto.getBoardId()).orElse(null);
            if (board != null) {
                // 엔티티의 필드를 업데이트합니다.
                board.setBoardCount(board.getBoardCount());
                // Repository를 통해 엔티티를 저장 또는 업데이트합니다.
                boardRepository.save(board);
            }
        }

    public void incrementBoardCount(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));
        if (board != null) {
            board.setBoardCount(board.getBoardCount() + 1);
            boardRepository.save(board);
        }
    }


    public Integer saveBoard(BoardFormDto boardFormDto) throws  Exception{
        Board board = boardFormDto.createBoard();
        boardRepository.save(board);
        return board.getBoardId();
    }


}


