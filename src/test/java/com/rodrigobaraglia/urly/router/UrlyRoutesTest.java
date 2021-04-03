package com.rodrigobaraglia.urly.router;

import com.rodrigobaraglia.urly.handler.UrlyHandler;
import com.rodrigobaraglia.urly.model.dto.RankingDTO;
import com.rodrigobaraglia.urly.model.dto.VisitsDTO;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@SpringBootTest
class UrlyRoutesTest {

    @Autowired
    ReactiveRedisConnectionFactory connectionFactory;
    @Autowired
    private UrlyRoutes routes;
    @Autowired
    private UrlyHandler handler;
    String ranking = "UrlVisitRanking";
    WebTestClient testClient;
    String url = "https://developer.mozilla.org/en-US/docs/Web/HTTP/Redirections";
    String shortPath = "/bnAwTh";

    @BeforeEach
    void init() {
        testClient = WebTestClient.bindToRouterFunction(routes.route(handler)).build();
    }

    @Test
    void testCreateUrl() {
        testClient.post().uri("/").bodyValue(url)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(String.class)
                .getResponseBody()
                .subscribe(System.out::println);
    }

    @Test
    void testGoToUrl() {
        testClient.post().uri("/").bodyValue(url)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(String.class)
                .getResponseBody().map(s -> testClient.get()
                .uri("/" + s).exchange()
                .expectStatus().isPermanentRedirect()
                .expectHeader().location(url))
                .subscribe(responseSpec ->
                        System.out.println(responseSpec
                                .returnResult(String.class)
                                .getUrl()));

    }


    @Test
    void testGetVisits() {
        var t = testClient.get().uri(shortPath + "/visits").exchange()
                .expectStatus().isOk().expectBody(VisitsDTO.class).returnResult().getResponseBody();
        Assertions.assertTrue(Double.valueOf(t.getVisits()) > 0);
    }

    @Test
    void testTop10() {
        var result = testClient.get().uri("/top").exchange()
                .returnResult(RankingDTO.class).getResponseBody();
        StepVerifier.create(result.log())
                .expectNextMatches(x -> x.getMostVisited().isEmpty() == false).verifyComplete();

    }


    @Disabled
    @After
    void cleanUp() {
        connectionFactory.getReactiveConnection().serverCommands().flushAll();

    }

}