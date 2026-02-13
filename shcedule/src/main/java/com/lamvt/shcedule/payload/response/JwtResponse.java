package com.lamvt.shcedule.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;

}