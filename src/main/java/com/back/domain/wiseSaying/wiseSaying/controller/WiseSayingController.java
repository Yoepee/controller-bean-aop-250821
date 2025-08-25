package com.back.domain.wiseSaying.wiseSaying.controller;

import com.back.domain.wiseSaying.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.wiseSaying.service.WiseSayingService;
import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/wiseSayings")
@Controller
@RequiredArgsConstructor
public class WiseSayingController {
    private final WiseSayingService wiseSayingService;
    private final Parser parser;
    private final HtmlRenderer renderer;

    @GetMapping("/write")
    @ResponseBody
    public String write(String content, String author) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("content is null or blank");
        }

        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("author is null or blank");
        }

        WiseSaying wiseSaying = wiseSayingService.write(content, author);

        return "%d번 명언이 생성되었습니다.".formatted(wiseSaying.getId());
    }

    @GetMapping("")
    @ResponseBody
    public String list() {
        List<WiseSaying> wiseSayings = wiseSayingService.findAll();
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
        WiseSaying wiseSaying = wiseSayingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("%d번 명언이 없습니다.".formatted(id)));

        wiseSayingService.delete(wiseSaying);
        
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

        WiseSaying wiseSaying = wiseSayingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("%d번 명언이 없습니다.".formatted(id)));

        wiseSayingService.modify(wiseSaying, content, author);

        return "%d번 명언이 수정되었습니다.".formatted(id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String detail(@PathVariable int id) {
        WiseSaying wiseSaying = wiseSayingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("%d번 명언이 없습니다.".formatted(id)));
        
        // 문자열 파싱해서 Node 트리 구조로 변환
        Node document = parser.parse(wiseSaying.getContent());
        
        // Node를 HTML 문자열로 렌더링
        String html = renderer.render(document);

        return """
                <h1>번호: %d</h1>
                <div>작가: %s</div>
                <div>%s</div>
                """.formatted(wiseSaying.getId(), wiseSaying.getAuthor(), html);
    }
}
