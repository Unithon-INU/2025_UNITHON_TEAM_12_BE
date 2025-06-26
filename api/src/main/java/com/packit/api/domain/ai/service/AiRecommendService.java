package com.packit.api.domain.ai.service;

import com.packit.api.common.exception.NotFoundException;
import com.packit.api.domain.ai.client.AiRecommendApiClient;
import com.packit.api.domain.ai.client.dto.AiRecommendApiRequest;
import com.packit.api.domain.ai.dto.request.AiRecommendRequest;
import com.packit.api.domain.ai.dto.response.AiRecommendedItemResponse;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiRecommendService {

    private final AiRecommendApiClient aiRecommendApiClient; // FastAPI 연동
    private final TripRepository tripRepository;
    private final TripCategoryRepository tripCategoryRepository;
    private final TripItemRepository tripItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<AiRecommendedItemResponse> recommendItems(Long userId, AiRecommendRequest request) {
        Trip trip = tripRepository.findByIdAndUserId(request.tripId(), userId)
                .orElseThrow(() -> new NotFoundException("여행을 찾을 수 없습니다."));

        TripCategory category = tripCategoryRepository.findByIdAndTripId(request.tripCategoryId(), trip.getId())
                .orElseThrow(() -> new NotFoundException("카테고리를 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));

        // FastAPI 호출용 DTO 구성
        AiRecommendApiRequest apiRequest = AiRecommendApiRequest.from(user, trip, category);

        // FastAPI 호출
        List<AiRecommendedItemResponse> response = aiRecommendApiClient.requestRecommendations(apiRequest);

        // TripItem 저장
        for (AiRecommendedItemResponse item : response) {
            TripItem tripItem = TripItem.ofAiGenerated(
                    category,
                    item.name(),
                    item.quantity()
            );
            tripItemRepository.save(tripItem);
        }

        return response;
    }
}
