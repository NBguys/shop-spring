package jpabook.jpashop.api;


import jakarta.validation.Valid;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jpabook.jpashop.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse createMemberV1(@RequestBody @Valid Member member) {
        Long memberId = memberService.join(member);

        return new CreateMemberResponse(memberId);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse createMemberV2(@RequestBody @Valid CreateMemberRequest createMemberRequest) {
        Member member = new Member();
        member.setName(createMemberRequest.getName());

        Long memberId = memberService.join(member);

        return new CreateMemberResponse(memberId);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody
                                               @Valid UpdateMemberRequest updateMember) {

        memberService.findUpdate(id, updateMember.getName());
        Member member = memberService.findOne(id);

        return new UpdateMemberResponse(member.getId(), member.getName());

    }

    @GetMapping("/api/v2/members")
    public Result selectMemberV2() {
        List<Member> members = memberService.findMembers();

        List<MemberDto> collect = members.stream().map(member -> new MemberDto(member.getId(), member.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    @Data
    private static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    private static class CreateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    private static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    private static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    private static class MemberDto {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    private static class Result<T> {
        private int count;
        private T data;
    }

}
