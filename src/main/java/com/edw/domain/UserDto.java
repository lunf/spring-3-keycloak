package com.edw.domain;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserDto {
    private String displayName;
    private String email;
    private String accountName;
    private Instant pwdLastSet;
}
