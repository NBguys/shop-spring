package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired private MemberService memberService;


    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("Lee");

        Long memberId = memberService.join(member);

        Assertions.assertEquals(member, memberService.findOne(memberId));

    }

    @Test()
    void 중복회원체크() {
        Member member1 = new Member();
        member1.setName("Lee");

        Member member2 = new Member();
        member2.setName("Lee");

        memberService.join(member1);

        Assertions.assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));

    }
}