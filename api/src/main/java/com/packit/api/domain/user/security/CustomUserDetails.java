package com.packit.api.domain.user.security;


import com.packit.api.domain.user.entity.User;
import com.packit.api.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long id; // id 타입을 Long으로 변경
    private final String email;
    private final String password;
    private final String nickname;
    private final UserRole role;
    private final Collection<? extends GrantedAuthority> authorities;

    @Builder
    public CustomUserDetails(Long id, String email, String password, String nickname, UserRole role, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.authorities = authorities;
    }

    public static CustomUserDetails from(User user) {
        return CustomUserDetails.builder()
                .id(user.getId())  // id 타입을 Long으로 맞춤
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .role(user.getUserRole())
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name())))
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Spring Security에서 사용하는 유저네임은 email로 사용
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
