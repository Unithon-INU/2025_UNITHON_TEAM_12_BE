package com.packit.api.domain.ai.client;

import com.packit.api.domain.ai.client.dto.AiRecommendApiRequest;
import com.packit.api.domain.ai.client.dto.AiRecommendApiResponse;
import com.packit.api.domain.ai.dto.response.AiRecommendedItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AiRecommendApiClient {

    private final WebClient webClient;

    @Value("${ai.recommendation.url}")
    private String recommendUrl;

    public List<AiRecommendApiResponse> requestRecommendations(AiRecommendApiRequest request) {
        return webClient.post()
                .uri(recommendUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(AiRecommendApiResponse.class)//  변환
                .collectList()
                .block();
    }
}
