package com.packit.api.domain.user.entity;

import com.packit.api.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users") // 테이블 이름 지정 (선택 사항)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL의 AUTO_INCREMENT 적용
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true) // 중복 방지를 위해 unique 추가
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nickname;
    @NotBlank
    @Column(nullable = false)
    private String name; // 추가됨

    @NotNull
    @Column(nullable = false)
    private Integer age; // 추가됨

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender; // 추가됨

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Builder
    public User(String email, String password, String nickname, String name, Integer age, Gender gender, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.userRole = userRole;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}