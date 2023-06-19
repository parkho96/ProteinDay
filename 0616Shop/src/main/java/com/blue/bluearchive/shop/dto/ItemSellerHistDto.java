package com.blue.bluearchive.shop.dto;

import com.blue.bluearchive.constant.OrderSellerStatus;
import com.blue.bluearchive.shop.entity.OrderItem;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ItemSellerHistDto {

    private String itemNm;
    private String imgUrl;
    private String buyerNm;
    private String buyerAdr;
    private LocalDateTime regTime;
    private Integer itemPrice;
    private Integer count;
    private Integer totalPrice;
    private Integer stockNumber;
    private OrderSellerStatus orderSellerStatus;

    @QueryProjection
    public ItemSellerHistDto(String itemNm, String imgUrl, String nickName, String address, OrderItem orderItem){
        this.itemNm = itemNm;
        this.imgUrl = imgUrl;
        this.buyerNm = nickName;
        this.buyerAdr = address;
        this.regTime = orderItem.getRegTime();
        this.itemPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
        this.totalPrice = orderItem.getTotalPrice();
        this.stockNumber = orderItem.getStockNumber();
        this.orderSellerStatus = orderItem.getOrderSellerStatus();

    }


}
