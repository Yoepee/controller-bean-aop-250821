package com.back;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Configuration
@Transactional
public class AppConfig {
    @Autowired
    @Lazy
    private AppConfig self;
    private final MemberService memberService;

    @Bean
    PersonRepository personRepository() {
        return new PersonRepository(1);
    }

    @Bean
    int version() {
        return 3;
    }

    @Bean
    ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    void work1() {
        if (memberService.count() > 0) return;

        memberService.join("system", "1234", "시스템");
        memberService.join("admin", "1234", "관리자");
        memberService.join("user1", "1234", "사용자 1");
        memberService.join("user2", "1234", "사용자 2");
        memberService.join("user3", "1234", "사용자 3");
        memberService.join("user4", "1234", "사용자 4");
    }

    void work2() {
        Member memberUser2 = memberService.findByUsername("user2").get();

        memberUser2.setNickname("유저2 New Nickname");
    }
}
