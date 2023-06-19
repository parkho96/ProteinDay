package com.blue.bluearchive.member.service;

import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member fundMember = memberRepository.findByEmail(member.getEmail());
        if(fundMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if(member == null) {
            throw new UsernameNotFoundException(email);
        }


        return CustomUserDetails.builder()
                .idx(member.getIdx())
                .id(member.getId())
                .name(member.getName())
                .password(member.getPassword())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .address(member.getAddress())
                .phoneNum(member.getPhoneNum())
                .role(member.getRole())
                .grade(member.getGrade())
                .memberStat(member.getMemberStat())
                .createdBy(member.getCreatedBy())
                .build();
    }
}
