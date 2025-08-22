package com.back.domain.member.member.entity;

import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;

    public Member(String username, String password, String nickname) {
        this.username = username;
        this.password = encryptPassword(password);
        this.nickname = nickname;
    }

    private String encryptPassword(String password) {
        String encryptedPassword = String.valueOf((password+"secret24").hashCode());
        return Base64.getEncoder().encodeToString((password+"salt").getBytes());
    }
}
