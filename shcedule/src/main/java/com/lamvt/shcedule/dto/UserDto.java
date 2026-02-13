package com.lamvt.shcedule.dto;

import com.lamvt.shcedule.constraint.EStatus;
import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private String is;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private EStatus status;

}
