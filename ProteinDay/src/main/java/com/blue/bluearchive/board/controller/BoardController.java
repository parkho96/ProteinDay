package com.blue.bluearchive.board.controller;

import com.blue.bluearchive.board.dto.*;
import com.blue.bluearchive.board.entity.Category;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.service.BoardService;
import com.blue.bluearchive.board.service.CategoryService;
import com.blue.bluearchive.board.service.CommentService;
import com.blue.bluearchive.board.service.CommentsCommentService;
import com.blue.bluearchive.member.dto.MemberDto;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {
    private final BoardService boardService;
    private final CategoryService categoryService;

    private final CommentService commentService;

    private final CommentsCommentService commentsCommentService;

    private final MemberRepository memberRepository;

    public BoardController(BoardService boardService, CategoryService categoryService, CommentService commentService, CommentsCommentService commentsCommentService, MemberRepository memberRepository) {
        this.boardService = boardService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.commentsCommentService = commentsCommentService;
        this.memberRepository = memberRepository;
    }
    @GetMapping(value = "/board/all")
    public String getBoardList(Model model) {
        List<BoardDto> boardList = boardService.getAllBoards();
        model.addAttribute("boardList", boardList);



        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        // 각 게시물의 댓글 수와 대댓글 수를 조회하여 모델에 추가

        return "board/list";
    }


    // POST 방식으로 "/board/{categoryId}" 경로에 접근할 때의 처리
    @GetMapping(value = "/board/{categoryId}")
    public String getBoardsByCategory(@PathVariable int categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId); // categoryId에 해당하는 카테고리를 가져옵니다.
        List<BoardDto> boardList = boardService.getBoardsByCategory(category); // 해당 카테고리에 속한 게시물들을 가져옵니다.
        model.addAttribute("boardList", boardList); // boardList를 모델에 추가합니다.


        List<CategoryDto> categoryList = categoryService.getAllCategory(); // 모든 카테고리를 가져옵니다.
        model.addAttribute("categoryList", categoryList); // categoryList를 모델에 추가합니다.


        return "board/list"; // board/list 뷰를 반환합니다.
    }
    @GetMapping("/boardDetail/{boardId}")
    public String getBoardDetails(@PathVariable int boardId, Model model) {
        boardService.incrementBoardCount(boardId);
        BoardDto board = boardService.getBoardById(boardId);
        model.addAttribute("board", board);
        List<CommentDto> commentList = commentService.getCommentByBoardId(boardId);
        model.addAttribute("commentList", commentList);
        Map<Integer, List<CommentsCommentDto>> commentsCommentMap = new HashMap<>();
        for (CommentDto comment : commentList) {
            int commentId = comment.getCommentId();
            List<CommentsCommentDto> commentsCommentList = commentsCommentService.getCommentsCommentByCommentId(commentId);
            commentsCommentMap.put(commentId, commentsCommentList);
        }
        model.addAttribute("commentsCommentMap", commentsCommentMap);

        return "board/boardDetail";
    }
    @GetMapping(value = "/boardWrite/new")
    public String getBoardWrite(Model model){
        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("BoardFormDto",new BoardFormDto());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email);
        model.addAttribute("member",member);
        return "board/boardWrite2";
    }
    @PostMapping(value = "/boardWrite/new")
    public String boardNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage","게시글 등록 중 오류");
            System.out.println("엥");
            return "/board/boardWrite2";
        }
        try {
            boardService.saveBoard(boardFormDto);
        }catch (Exception e){
            model.addAttribute("errorMessage","게시글 등록 중 오류");
        }
        System.out.println(boardFormDto.getMember_idx());
        return "redirect:/board/"+boardFormDto.getCategory().getCategoryId();
    }
    @PostMapping("/boardWirte/upload")
    public String uploadFiles(@RequestPart("files") MultipartFile[] files) {
        // 파일 업로드 처리
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                return "Failed to upload files";
            }
        }

        // 업로드 성공 메시지 반환
        return "Files uploaded successfully";
    }
}
