package com.rodrigobaraglia.urly.handler;

import com.rodrigobaraglia.urly.model.Url;
import com.rodrigobaraglia.urly.repository.UrlyRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
                .flatMap(savedUrl -> ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).bodyValue(savedUrl.getId()))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error));
    }

    public Mono<ServerResponse> visit(ServerRequest request) {
        String shortUrl = request.pathVariable("shorturl");
        return repository.getAndUpdateUrl(shortUrl)
                .flatMap(url -> ServerResponse.temporaryRedirect(URI.create(url)).build())
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue("Error 404: Not found"));
    }

    public Mono<ServerResponse> getVisits(ServerRequest request) {
        String shortUrl = request.pathVariable("shorturl");
        return repository.getVisitCount(shortUrl)
                .flatMap(urlDTO -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(urlDTO)
                        .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue("Error 404: Not found")));
    }

    public Mono<ServerResponse> getRanking(ServerRequest request) {
        return repository.getMostVisited().flatMap(data -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .bodyValue(data))
                .switchIfEmpty(ServerResponse.accepted().bodyValue("There are no URLs to show yet. Why don't you create some?"))
                .onErrorResume(error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(error));
    }


}
