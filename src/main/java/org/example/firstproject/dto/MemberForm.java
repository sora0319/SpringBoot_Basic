package org.example.firstproject.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.example.firstproject.entity.Member;

@AllArgsConstructor
@ToString
public class MemberForm {
    private String email;

    private String password;

    public Member toEntity() {
        return new Member(null, email, password);
    }
}
