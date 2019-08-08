package com.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateProfileRequest extends BaseRequest {

    @NotEmpty(message = "Name is mandatory!")
    String name;

    @Size(max = 4)
    String doy;

    @NotEmpty(message = "Please provide a valid mobile number!")
    String mobile;

    @Email(message = "Please provide a valid email!")
    String email;
}
