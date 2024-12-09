package com.novigo.demo_park_api.web.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPasswordDto {

    @NotBlank
    @Size(min = 6, max = 6)
    private String actualPass;

    @NotBlank
    @Size(min = 6, max = 6)
    private String newPass;

    @NotBlank
    @Size(min = 6, max = 6)
    private String confirmPass;
}
