package com.blue.bluearchive.board.dto;

import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Category;
import com.blue.bluearchive.member.entity.Member;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

import java.util.List;
@Data
public class BoardFormDto {
    @NotBlank(message = "제목은 필수 입니다")
    private String boardTitle;
    @NotBlank(message = "내용은 필수 입니다")
    private String boardContent;

    private Category category;

    private String createdBy;

    private Member member_idx;

//    private List<MultipartFile> images;
//    private List<MultipartFile> activityImages;
//    private List<MultipartFile> bodyImages;
//    private List<MultipartFile> foodImages;
//    private List<MultipartFile> placeImages;

    private static ModelMapper modelMapper = new ModelMapper();

    public Board createBoard(){
        return modelMapper.map(this, Board.class);
    }
    public static BoardFormDto of(Board board){
        return modelMapper.map(board, BoardFormDto.class);
    }

}
