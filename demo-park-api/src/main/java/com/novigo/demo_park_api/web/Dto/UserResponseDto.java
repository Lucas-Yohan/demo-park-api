package com.novigo.demo_park_api.web.Dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDto {

    private Long id;
    private String username;
    private String role;
}
