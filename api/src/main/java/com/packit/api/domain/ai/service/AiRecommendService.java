package com.packit.api.domain.ai.service;

import com.packit.api.common.exception.NotFoundException;
import com.packit.api.domain.ai.client.AiRecommendApiClient;
import com.packit.api.domain.ai.client.dto.AiRecommendApiRequest;
import com.packit.api.domain.ai.client.dto.AiRecommendApiResponse;
import com.packit.api.domain.ai.dto.request.AiRecommendRequest;
import com.packit.api.domain.ai.dto.response.AiRecommendedCategoryResponse;
import com.packit.api.domain.ai.dto.response.AiRecommendedItemResponse;
import com.packit.api.domain.ai.entity.AiRecommendationItemLog;
import com.packit.api.domain.ai.repository.AiRecommendationItemLogRepository;
import com.packit.api.domain.trip.entity.Trip;
import com.packit.api.domain.trip.repository.TripRepository;
import com.packit.api.domain.tripCategory.entity.TripCategory;
import com.packit.api.domain.tripCategory.repository.TripCategoryRepository;
import com.packit.api.domain.tripItem.entity.TripItem;
import com.packit.api.domain.tripItem.repository.TripItemRepository;
import com.packit.api.domain.user.entity.User;
import com.packit.api.domain.user.exception.UserNotFoundException;
import com.packit.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AiRecommendService {

    private final AiRecommendApiClient aiRecommendApiClient;
    private final TripRepository tripRepository;
    private final TripCategoryRepository tripCategoryRepository;
    private final TripItemRepository tripItemRepository;
    private final UserRepository userRepository;
    private final AiRecommendationItemLogRepository aiRecommendationItemLogRepository;


    @Transactional
    public List<AiRecommendedCategoryResponse> recommendItems(Long userId, AiRecommendRequest request) {
        Trip trip = getTripOrThrow(request.tripId(), userId);
        User user = getUserOrThrow(userId);

        AiRecommendApiRequest apiRequest = AiRecommendApiRequest.from(user, trip);

        // AI 추천 호출
        List<AiRecommendApiResponse> aiResponseList = requestRecommendationsFromAi(apiRequest);

        // DB 저장 + 응답 변환
        return saveAndConvertToResponse(aiResponseList, trip);
    }

    private Trip getTripOrThrow(Long tripId, Long userId) {
        return tripRepository.findByIdAndUserId(tripId, userId)
                .orElseThrow(() -> new NotFoundException("여행을 찾을 수 없습니다."));
    }

    private TripCategory getTripCategoryOrThrow(Long categoryId, Long tripId) {
        return tripCategoryRepository.findByIdAndTripId(categoryId, tripId)
                .orElseThrow(() -> new NotFoundException("카테고리를 찾을 수 없습니다."));
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
    }

    private List<AiRecommendApiResponse> requestRecommendationsFromAi(AiRecommendApiRequest apiRequest) {
        return aiRecommendApiClient.requestRecommendations(apiRequest);
    }

    private List<AiRecommendedCategoryResponse> saveAndConvertToResponse(
            List<AiRecommendApiResponse> apiResponses,
            Trip trip
    ) {
        List<AiRecommendedCategoryResponse> result = new ArrayList<>();

        for (AiRecommendApiResponse apiResponse : apiResponses) {
            String categoryName = apiResponse.getCategory();

            // TripCategory 조회 (카테고리 이름 기반)
            TripCategory category = tripCategoryRepository.findByTripIdAndName(trip.getId(), categoryName)
                    .orElseThrow(() -> new NotFoundException("해당 여행에 '" + categoryName + "' 카테고리가 존재하지 않습니다."));

            List<AiRecommendedItemResponse> items = apiResponse.getItems().stream()
                    .map(AiRecommendedItemResponse::from)
                    .toList();

            for (AiRecommendedItemResponse item : items) {
                AiRecommendationItemLog log = AiRecommendationItemLog.builder()
                        .tripCategory(category)
                        .name(item.name())
                        .quantity(item.quantity())
                        .createdAt(LocalDateTime.now())
                        .build();
                aiRecommendationItemLogRepository.save(log);
            }
            result.add(AiRecommendedCategoryResponse.from(categoryName, items));
        }
        return result;
    }

    public List<AiRecommendedItemResponse> getRecommendationsByTripCategory(Long tripCategoryId) {
        TripCategory category = tripCategoryRepository.findById(tripCategoryId)
                .orElseThrow(() -> new NotFoundException("카테고리를 찾을 수 없습니다."));

        List<AiRecommendationItemLog> logs = aiRecommendationItemLogRepository.findByTripCategory(category);

        return logs.stream()
                .map(log -> new AiRecommendedItemResponse(log.getName(), log.getQuantity()))
                .toList();
    }
}
