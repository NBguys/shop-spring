package jpabook.jpashop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "이름은 필수 입력 항목입니다")
    String name;

    String city;
    String street;
    String zipcode;

}
