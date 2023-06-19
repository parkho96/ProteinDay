package com.blue.bluearchive.shop_inquiry.controller;

import com.blue.bluearchive.shop_inquiry.service.InquiryAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/item/inquiryAnswer")
@RequiredArgsConstructor
public class inquiryAnswerController {

    private final InquiryAnswerService inquiryAnswerService;

    @PostMapping("/{inquiryId}")
    public String submitAnswer(@PathVariable("inquiryId")Long inquiryId, HttpServletRequest request, RedirectAttributes attributes) {
        String answerContent = request.getParameter("answerContent");
        String itemId = request.getParameter("itemId");

        if (answerContent == null || answerContent.trim().isEmpty()) {
            attributes.addFlashAttribute("error", "답변 내용을 입력해주세요.");
            return "redirect:/member/item/" + itemId;
        } else {
            inquiryAnswerService.answerWrite(inquiryId, answerContent);
            attributes.addFlashAttribute("message", "문의가 성공적으로 등록되었습니다.");
            return "redirect:/member/item/" + itemId;
        }

    }

}
