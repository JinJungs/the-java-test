package me.whiteship.section1.member;


import me.whiteship.section1.domain.Member;
import me.whiteship.section1.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId) throws MemberNotFoundException;

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member member);
}
