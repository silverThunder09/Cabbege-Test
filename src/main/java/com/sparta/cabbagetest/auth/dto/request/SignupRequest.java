package com.sparta.cabbagetest.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 72, message = "비밀번호는 8자 이상 72자 이하로 입력할 수 있습니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(max = 100, message = "닉네임은 최대 100자까지 입력할 수 있습니다.")
    private String nickname;

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 100, message = "이름은 최대 100자까지 입력할 수 있습니다.")
    private String name;

    @Size(max = 30, message = "전화번호는 최대 30자까지 입력할 수 있습니다.")
    private String phone;
}
