package com.packit.api.domain.user.service;

import com.packit.api.domain.trip.repository.TripRepository;
import com.packit.api.domain.trip.service.TripService;
import com.packit.api.domain.user.dto.response.MyPageResponse;
import com.packit.api.domain.trip.dto.response.TripSummaryResponse;
import com.packit.api.domain.user.entity.User;
import com.packit.api.domain.user.exception.UserNotFoundException;
import com.packit.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;
    private final TripService tripService;

    public MyPageResponse getMyInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        return MyPageResponse.of(user.getEmail(), user.getNickname());
    }

    public TripSummaryResponse getTripSummary(Long userId) {
        return tripService.getTripSummaryByUser(userId);
    }
}