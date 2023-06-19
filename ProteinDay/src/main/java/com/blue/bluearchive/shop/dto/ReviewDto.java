package com.blue.bluearchive.shop.dto;

import com.blue.bluearchive.shop.entity.ReviewImg;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReviewDto {

    private Long id;
    private Long itemId;
    private String content;
    private String createdBy;
    private String nickName;
    private String star;
    private LocalDateTime regTime;
    private List<ReviewImg> reviewImgDtoList;

}
