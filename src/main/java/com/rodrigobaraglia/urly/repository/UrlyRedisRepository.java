package com.rodrigobaraglia.urly.repository;

import com.rodrigobaraglia.urly.model.Url;
import com.rodrigobaraglia.urly.model.dto.RankingDTO;
import com.rodrigobaraglia.urly.model.dto.VisitsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.time.temporal.ChronoUnit;


@Repository
public class UrlyRedisRepository {
    @Autowired
    ReactiveRedisOperations<String, String> operations;
    String ranking = "UrlVisitRanking";

    public Mono<Url> saveUrl(Url url) {

        return operations.opsForValue().setIfAbsent(url.getId(), url.getUrl(), Duration.of(30, ChronoUnit.DAYS))
                .then(operations.opsForZSet().add(ranking, url.getId(), 0)).thenReturn(url);
    }

    private Mono<Double> incrVisitCount(String shortUrl) {
       return operations.opsForZSet().incrementScore(ranking, shortUrl, 1);
    }

    private Mono<String> getLongUrl(String shortUrl) {
        return operations.opsForValue().get(shortUrl);
    }

    public Mono<String> getAndUpdateUrl(String shortUrl) {
        return getLongUrl(shortUrl).flatMap(url -> incrVisitCount(shortUrl).thenReturn(url));
    }

    public Mono<VisitsDTO> getVisitCount(String shortUrl) {
        return operations.opsForZSet()
                .score(ranking, shortUrl)
                .flatMap(visits -> operations.opsForValue().get(shortUrl)
                        .map(longUrl -> new VisitsDTO(shortUrl, longUrl, Math.round(visits))));
    }

    public Mono<RankingDTO> getMostVisited() {

        var results = operations.opsForZSet().reverseRangeWithScores(ranking,
                Range.<Long>from(Range.Bound.inclusive(Long.valueOf(0))).to(Range.Bound.exclusive(Long.valueOf(10))))
                .map(item -> item.getValue()).collectList().map(l -> new RankingDTO(l));
        return results;

    }


}
