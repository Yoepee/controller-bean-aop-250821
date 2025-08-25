package com.back.domain.wiseSaying.wiseSaying.controller;

import com.back.domain.wiseSaying.wiseSaying.entity.WiseSaying;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/wiseSayings")
@Controller
public class WiseSayingController {
    private int lastId = 0;
    private final List<WiseSaying> wiseSayings = new ArrayList<>() {{
        add(new WiseSaying(++lastId, "명언 1", "작가 1"));
        add(new WiseSaying(++lastId, "명언 2", "작가 2"));
        add(new WiseSaying(++lastId, "명언 3", "작가 3"));
        add(new WiseSaying(++lastId, "명언 4", "작가 4"));
        add(new WiseSaying(++lastId, "명언 5", "작가 5"));
    }};

    @GetMapping("/write")
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

    @GetMapping("")
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
    
    @GetMapping("/{id}/delete/")
    @ResponseBody
    public String delete(@PathVariable int id) {
        WiseSaying wiseSaying = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("%d번 명언이 없습니다.".formatted(id)));

        wiseSayings.remove(wiseSaying);
        
        return "%d번 명언이 삭제되었습니다.".formatted(id);
    }

    @GetMapping("/{id}/modify")
    @ResponseBody
    public String modify(@PathVariable int id, String content, String author) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("content is null or blank");
        }

        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("author is null or blank");
        }

        WiseSaying wiseSaying = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("%d번 명언이 없습니다.".formatted(id)));

        modify(wiseSaying, content, author);

        return "%d번 명언이 수정되었습니다.".formatted(id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String detail(@PathVariable int id) {
        WiseSaying wiseSaying = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("%d번 명언이 없습니다.".formatted(id)));

        return """
                <h1>%d. %s</h1>
                <div>작가: %s</div>
                """.formatted(wiseSaying.getId(), wiseSaying.getContent(), wiseSaying.getAuthor());
    }
    
    private Optional<WiseSaying> findById (int id) {
        return wiseSayings.stream()
                .filter(wiseSaying -> wiseSaying.getId() == id)
                .findFirst();
    }

    private void modify(WiseSaying wiseSaying, String content, String author){
        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);
    }
}
