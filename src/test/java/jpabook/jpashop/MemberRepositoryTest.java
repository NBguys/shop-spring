package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void addMember() {
        Member member = new Member();
        member.setUsername("user1");

        Long savedId = memberRepository.save(member);
        Member findedMember = memberRepository.find(savedId);

        Assertions.assertThat(findedMember).isEqualTo(member);

    }
}