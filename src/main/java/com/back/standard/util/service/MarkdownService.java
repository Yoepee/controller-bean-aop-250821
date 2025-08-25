package com.back.standard.util.service;

import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarkdownService {
    private final Parser parser;
    private final HtmlRenderer renderer;
    
    MarkdownService() {
        this.parser = Parser.builder().build();
        this.renderer = HtmlRenderer.builder().build();
    }

    public String toHtml(String markdown) {
        // 문자열 파싱해서 Node 트리 구조로 변환
        Node document = parser.parse(markdown);

        // Node를 HTML 문자열로 렌더링
        String html = renderer.render(document);

        return html;
    }
}
