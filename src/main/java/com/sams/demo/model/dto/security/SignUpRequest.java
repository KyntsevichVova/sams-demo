package com.sams.demo.model.dto.security;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.sams.demo.model.error.ErrorCode.FIELD_EMPTY;
import static com.sams.demo.model.error.ErrorCode.FIELD_MISSING;

@Data
@EqualsAndHashCode(of = {"username", "email"})
@ToString(of = {"username", "email"})
public class SignUpRequest {

    @NotNull(message = FIELD_MISSING)
    @NotBlank(message = FIELD_EMPTY)
    //@Size(min = ?, max = ?)
    //Email
    private String email;

    @NotNull(message = FIELD_MISSING)
    @NotBlank(message = FIELD_EMPTY)
    //@Size(min = ?, max = ?)
    private String username;

    @NotNull(message = FIELD_MISSING)
    @NotBlank(message = FIELD_EMPTY)
    //@Size(min = ?, max = ?)
    private String password;
}