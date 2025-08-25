package com.back.domain.wiseSaying.wiseSaying.controller;

import com.back.domain.wiseSaying.wiseSaying.entity.WiseSaying;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WiseSayingController {
    private int lastId = 0;
    private final List<WiseSaying> wiseSayings = new ArrayList<>();

    @GetMapping("/wiseSayings/write")
    @ResponseBody
    public String write(String content, String author) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("content is null or blank");
        }

        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("author is null or blank");
        }

        int id = ++lastId;
        WiseSaying wiseSaying = new WiseSaying(id, content, author);

        wiseSayings.add(wiseSaying);

        return "%d번 명언이 생성되었습니다.".formatted(id);
    }

    @GetMapping("wiseSayings")
    @ResponseBody
    public String list() {
        return "<ul>"
                + wiseSayings
                .stream()
                .map(wiseSaying ->
                        "<li>%d / %s / %s </li>".formatted(wiseSaying.getId(), wiseSaying.getContent(), wiseSaying.getAuthor())
                )
                .collect(Collectors.joining(""))
                + "</ul>";
    }
}
