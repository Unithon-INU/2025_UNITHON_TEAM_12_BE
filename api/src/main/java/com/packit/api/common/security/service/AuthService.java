package com.packit.api.common.security.service;

import com.packit.api.common.security.dto.SignupRequestDto;
import com.packit.api.domain.user.entity.User;
import com.packit.api.domain.user.entity.UserRole;
import com.packit.api.domain.user.exception.EmailAlreadyExistsException;
import com.packit.api.domain.user.exception.NicknameAlreadyExistsException;
import com.packit.api.domain.user.exception.UserCreateFailException;
import com.packit.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(SignupRequestDto dto) {
        validateUserCreateRequest(dto);

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = User.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .nickname(dto.getNickname())
                .name(dto.getName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .userRole(UserRole.USER)
                .build();


        userRepository.save(user);
        log.info("[signUpUser] SIGN UP SUCCESS!! : {}", user.getEmail());
    }

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

    private void validateUserCreateRequest(SignupRequestDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent())
            throw new UserCreateFailException("이미 존재하는 이메일입니다.");
    }
}