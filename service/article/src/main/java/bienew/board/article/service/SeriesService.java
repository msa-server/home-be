package bienew.board.article.service;

import bienew.board.article.entity.Series;
import bienew.board.article.repository.SeriesRepository;
import bienew.board.article.service.request.SeriesCreateRequest;
import bienew.board.article.service.request.SeriesUpdateRequest;
import bienew.board.article.service.response.SeriesResponse;
import bienew.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeriesService {
    private final SeriesRepository seriesRepository;
    private final Snowflake snowflake = new Snowflake();

    @Transactional
    public SeriesResponse create(SeriesCreateRequest seriesCreateRequest) {
        return SeriesResponse.from(
                seriesRepository.findBySeriesName(seriesCreateRequest.seriesName()).orElseGet(
                        () -> {
                            try {
                                // 저장 및 반영 동시에 진행.
                                return seriesRepository.saveAndFlush(Series.create(
                                        snowflake.nextId(),
                                        seriesCreateRequest.seriesName()
                                ));
                            } catch (DataIntegrityViolationException e) {
                                // 이미 중복 데이터(태그 이름 중복)를 넣을 시 발생.
                                // 새로 조회하여 전송.
                                return seriesRepository.findBySeriesName(seriesCreateRequest.seriesName())
                                        .orElseThrow();
                            }
                        }
                )
        );
    }

    public List<SeriesResponse> readAll() {
        return seriesRepository.findAll().stream()
                .map(SeriesResponse::from)
                .toList();
    }

    @Transactional
    public SeriesResponse update(Long seriesId, SeriesUpdateRequest request) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow();

        series.update(request.seriesName());

        return SeriesResponse.from(seriesRepository.save(series));
    }
}
