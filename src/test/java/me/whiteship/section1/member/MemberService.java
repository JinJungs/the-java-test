package me.whiteship.section1.member;


import me.whiteship.section1.domain.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId) throws MemberNotFoundException;

    void validate(Long memberId);
}
