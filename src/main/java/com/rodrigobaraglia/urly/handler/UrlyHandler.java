package com.rodrigobaraglia.urly.handler;

import com.rodrigobaraglia.urly.model.Url;
import com.rodrigobaraglia.urly.repository.UrlyRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class UrlyHandler {

    @Autowired
    UrlyRedisRepository repository;

    public Mono<ServerResponse> createUrl(ServerRequest request) {
        return request.bodyToMono(String.class)
                .map(uri -> new Url(uri))
                .flatMap(repository::saveUrl)
                .flatMap(savedUrl -> ServerResponse.ok().bodyValue(savedUrl.getId()))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error));
    }

    public Mono<ServerResponse> visit(ServerRequest request) {
        String shortUrl = request.pathVariable("shorturl");
        return repository.getAndUpdateUrl(shortUrl)
                .flatMap(url -> ServerResponse.temporaryRedirect(URI.create(url)).build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getVisits(ServerRequest request) {
        String shortUrl = request.pathVariable("shorturl");
        return repository.getVisitCount(shortUrl)
                .flatMap(urlDTO -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(urlDTO)
                        .onErrorResume(error -> ServerResponse.notFound().build()));
    }

    public Mono<ServerResponse> getTop10(ServerRequest request) {
        return repository.getMostVisited().flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .bodyValue(data));
    }


}
