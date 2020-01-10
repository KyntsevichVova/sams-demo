package com.sams.demo.model.dto.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.sams.demo.common.ApplicationConstant.EMAIL_PATTERN;
import static com.sams.demo.model.error.ErrorCode.*;

@Data
@EqualsAndHashCode(of = {"email"})
@ToString(of = {"email"})
public class SignInRequest {

    @NotNull(message = FIELD_MISSING)
    @NotBlank(message = FIELD_EMPTY)
    @Email(regexp = EMAIL_PATTERN, message = EMAIL_NOT_VALID)
    private String email;

    @NotNull(message = FIELD_MISSING)
    @NotBlank(message = FIELD_EMPTY)
    @Size(min = 3, max = 255, message = FIELD_INVALID_LENGTH)
    private String password;
}