package bienew.board.article.service;

import bienew.board.article.entity.Tag;
import bienew.board.article.repository.TagRepository;
import bienew.board.article.service.request.TagCreateRequest;
import bienew.board.article.service.request.TagUpdateRequest;
import bienew.board.article.service.response.TagResponse;
import bienew.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final Snowflake snowflake = new Snowflake();

    @Transactional
    public TagResponse create(TagCreateRequest tagCreateRequest) {
        return TagResponse.from(
                tagRepository.findByTagName(tagCreateRequest.tagName()).orElseGet(
                        () -> {
                            try {
                                // 저장 및 반영 동시에 진행.
                                return tagRepository.saveAndFlush(Tag.create(
                                        snowflake.nextId(),
                                        tagCreateRequest.tagName()
                                ));
                            } catch (DataIntegrityViolationException e) {
                                // 이미 중복 데이터(태그 이름 중복)를 넣을 시 발생.
                                // 새로 조회하여 전송.
                                return tagRepository.findByTagName(tagCreateRequest.tagName())
                                        .orElseThrow();
                            }
                        }
                )
        );
    }

    public List<TagResponse> readAll() {
        return tagRepository.findAll().stream()
                .map(TagResponse::from)
                .toList();
    }

    @Transactional
    public TagResponse update(Long tagId, TagUpdateRequest request) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow();

        tag.update(request.tagName());

        return TagResponse.from(tagRepository.save(tag));
    }
}
