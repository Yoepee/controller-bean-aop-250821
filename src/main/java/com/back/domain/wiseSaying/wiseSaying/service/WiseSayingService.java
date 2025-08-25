package com.back.domain.wiseSaying.wiseSaying.service;

import com.back.domain.wiseSaying.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.wiseSaying.repository.WiseSayingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSaying write(String content, String author){
        return wiseSayingRepository.save(new WiseSaying(content, author));
    }

    public void delete(WiseSaying wiseSaying){
        wiseSayingRepository.delete(wiseSaying);
    }

    public Optional<WiseSaying> findById (int id) {
        return wiseSayingRepository.findById(id);
    }

    public void modify(WiseSaying wiseSaying, String content, String author){
        wiseSaying.modify(content, author);

        wiseSayingRepository.save(wiseSaying);
    }

    public List<WiseSaying> findAll() {
        return wiseSayingRepository.findAll();
    }

    public long count() {
        return wiseSayingRepository.count();
    }
}
