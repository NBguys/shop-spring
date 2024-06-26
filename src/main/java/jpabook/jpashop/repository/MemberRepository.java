package jpabook.jpashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import jpabook.jpashop.domain.*;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
}
