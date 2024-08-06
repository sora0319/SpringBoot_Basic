package org.example.firstproject.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.example.firstproject.entity.Member;

@AllArgsConstructor
@ToString
public class MemberForm {
    private Long id;
    private String email;

    private String password;

    public Member toEntity() {
        return new Member(id, email, password);
    }
}
