package br.com.software_engineering.application.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank(message = "Name cannot be blank!")
    @Size(min = 2, message = "Need at leat 2 characters!")
    private String name;

    @NotBlank(message = "E-mail cannot be blank!")
    private String email;

    @NotBlank(message = "Phone cannot be blank!")
    private String phone;
}
