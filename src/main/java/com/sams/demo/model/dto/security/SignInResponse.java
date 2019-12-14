package com.sams.demo.model.dto.security;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SignInResponse {

    private String token;
}