package com.sams.demo.model.dto.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.sams.demo.model.error.ErrorCode.FIELD_EMPTY;
import static com.sams.demo.model.error.ErrorCode.FIELD_MISSING;

@Data
@EqualsAndHashCode(of = {"email"})
@ToString(of = {"email"})
public class SignInRequest {

    @NotNull(message = FIELD_MISSING)
    @NotBlank(message = FIELD_EMPTY)
    //@Size(min = ?, max = ?)
    //Email
    private String email;

    @NotNull(message = FIELD_MISSING)
    @NotBlank(message = FIELD_EMPTY)
    //@Size(min = ?, max = ?)
    private String password;
}