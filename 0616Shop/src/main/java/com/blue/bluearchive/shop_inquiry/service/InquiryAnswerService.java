package com.blue.bluearchive.shop_inquiry.service;

import com.blue.bluearchive.shop_inquiry.dto.InquiryAnswerDto;
import com.blue.bluearchive.shop_inquiry.entity.Inquiry;
import com.blue.bluearchive.shop_inquiry.entity.InquiryAnswer;
import com.blue.bluearchive.shop_inquiry.repository.InquiryAnswerRepository;
import com.blue.bluearchive.shop_inquiry.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryAnswerService {

    @Autowired
    private final InquiryRepository inquiryRepository;

    @Autowired
    private final InquiryAnswerRepository inquiryAnswerRepository;


    @Transactional(readOnly = true)
    public List<InquiryAnswer> getInquiryAnswerListByInquiryId(Long inquiryId) {
        List<InquiryAnswer> inquiryAnswerList = new ArrayList<>();
        List<InquiryAnswerDto> inquiryAnswerDtoList = inquiryAnswerRepository.findByInquiryId(inquiryId);

        for (InquiryAnswerDto inquiryAnswerDto : inquiryAnswerDtoList) {
            InquiryAnswer inquiryAnswer = new InquiryAnswer();
            inquiryAnswer.setId(inquiryAnswerDto.getId());
            inquiryAnswer.setContent(inquiryAnswerDto.getContent());
            Inquiry inquiry = inquiryRepository.findById(inquiryAnswerDto.getInquiryId())
                    .orElseThrow(() -> new EntityNotFoundException("Inquiry not found"));
            inquiryAnswer.setInquiry(inquiry);
            inquiryAnswer.setId(inquiryAnswerDto.getId());
            inquiryAnswer.setRegTime(inquiryAnswerDto.getRegTime());
            inquiryAnswerList.add(inquiryAnswer);
        }

        return inquiryAnswerList;
    }

    @Transactional(readOnly = false)
    public void answerWrite(Long inquiryId, String answerContent) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 inquiry ID 입니다."));

        InquiryAnswer inquiryAnswer = new InquiryAnswer();

        inquiryAnswer.setInquiry(inquiry);
        if (answerContent != null) {
            inquiryAnswer.setContent(answerContent);
        } else {
            throw new IllegalArgumentException("답변 내용을 입력해주세요.");
        }
        inquiryAnswerRepository.save(inquiryAnswer);
    }


}
