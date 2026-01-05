// Member.java
package com.fluxmall.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Member {
    private Long id;
    private String username;
    private String password;      // 해시된 비밀번호 저장
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}