package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    void 상품주문() {
        Member member = getMember();

        Item item = getBook();

        int orderCount =2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Order order = orderRepository.findOne(orderId);

        //상품 상태는 오더
        Assertions.assertEquals(OrderStatus.ORDER, order.getStatus());
        //주문한 상품 종류 수가 정확해야 한다.
        Assertions.assertEquals(1, order.getOrderItems().size());
        //주문 가격은 가격 * 수량이다.
        Assertions.assertEquals(10000*2, order.getTotalPrice());
        //주문 수량만큼 재고가 줄어야 한다.
        Assertions.assertEquals(8, item.getStockQuantity());
    }

    @Test
    void 주문취소() {

        Member member = getMember();

        Item item = getBook();
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);
        Order getOrder = orderRepository.findOne(orderId);

        //주문 취소시 상태는 CANCEL 이다.
        Assertions.assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        //주문이 취소된 상품은 그만큼 재고가 증가해야 한다.
        Assertions.assertEquals(10, item.getStockQuantity());

    }

    @Test
    void 상품주문_재고수량초과() {

        Member member = getMember();
        Item item = getBook();
        int orderCount = 11;

        Assertions.assertThrows(NotEnoughStockException.class
                , () -> orderService.order(member.getId(), item.getId(), 11));
    }


    private Book getBook() {
        Book book = new Book();
        book.setName("JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);
        return book;
    }

    private Member getMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }


}