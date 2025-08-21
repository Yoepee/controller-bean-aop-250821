package com.back;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final PersonService personService;

    @GetMapping("/")
    @ResponseBody
    public String main() {
        long count = personService.count();
        return "테스트 // 사람수 : %d".formatted(count);
    }
}
