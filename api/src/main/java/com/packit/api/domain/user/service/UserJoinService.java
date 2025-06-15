package com.packit.api.domain.user.service;


import com.packit.api.common.exception.BadRequestException;
import com.packit.api.domain.user.dto.NicknameUpdateRequestDto;
import com.packit.api.domain.user.dto.PasswordCheckRequestDto;
import com.packit.api.domain.user.dto.PasswordUpdateRequestDto;
import com.packit.api.domain.user.entity.User;
import com.packit.api.domain.user.entity.UserRole;
import com.packit.api.domain.user.exception.*;
import com.packit.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserJoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void validateEmailAvailable(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("이미 사용 중인 이메일입니다.");
        }
    }

    public void validateNicknameAvailable(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new NicknameAlreadyExistsException("이미 사용 중인 닉네임입니다.");
        }
    }

    public void checkPassword(String email, PasswordCheckRequestDto requestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new NotSamePasswordException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void updatePassword(String email, PasswordUpdateRequestDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));

        //기존 비밀번호와 같을시 에러
        if (passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new BadRequestException("기존 비밀번호와 동일한 비밀번호는 사용할 수 없습니다.");
        }

        user.updatePassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);
    }


    public void updateNickname(String email, NicknameUpdateRequestDto dto) {
        if (userRepository.existsByNickname(dto.nickname())) {
            throw new NicknameAlreadyExistsException("이미 사용 중인 닉네임입니다.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("유저 정보를 찾을 수 없습니다."));

        user.updateNickname(dto.nickname());
        userRepository.save(user);
    }


}
