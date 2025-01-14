package com.fakestoreapi.clone.application.dto.request.user;

import com.fakestoreapi.clone.application.dto.validators.CreateValidatorGroup;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotNull(message = "The email is mandatory", groups = { CreateValidatorGroup.class })
    @Pattern(regexp = "^(?!\\s*$).+", message = "Email cannot be blank when provided")
    @Email(message = "The email must be a valid email address")
    private String email;

    @NotNull(message = "The user name is mandatory", groups = { CreateValidatorGroup.class })
    @Pattern(regexp = "^(?!\\s*$).+", message = "User name cannot be blank when provided")
    @Size(min = 3, max = 20, message = "The username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "The password is mandatory", groups = { CreateValidatorGroup.class })
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "The password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character.")
    private String password;

    @NotNull(message = "The name is mandatory")
    private Name name;

    @NotNull(message = "The address is mandatory")
    private Address address;

    @NotBlank(message = "The phone number is mandatory")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "The phone number must be a valid international phone number")
    private String phone;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Name {
        @NotNull(message = "The first name is mandatory", groups = { CreateValidatorGroup.class })
        @Pattern(regexp = "^(?!\\s*$).+", message = "First name cannot be blank when provided")
        @Size(max = 50, message = "The first name must not exceed 50 characters")
        private String firstname;

        @NotNull(message = "The last name is mandatory", groups = { CreateValidatorGroup.class })
        @Pattern(regexp = "^(?!\\s*$).+", message = "Last name cannot be blank when provided")
        @Size(max = 50, message = "The last name must not exceed 50 characters")
        private String lastname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Address {
        @NotNull(message = "The city is mandatory", groups = { CreateValidatorGroup.class })
        @Pattern(regexp = "^(?!\\s*$).+", message = "City cannot be blank when provided")
        private String city;

        @NotNull(message = "The street is mandatory", groups = { CreateValidatorGroup.class })
        @Pattern(regexp = "^(?!\\s*$).+", message = "Street cannot be blank when provided")
        private String street;

        @NotNull(message = "The house number is mandatory", groups = { CreateValidatorGroup.class })
        @Min(value = 1, message = "The house number must be greater than 0")
        private Integer number;

        @NotNull(message = "The zip code is mandatory", groups = { CreateValidatorGroup.class })
        @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "The zip code must be a valid format (e.g., 12345 or 12345-6789)")
        private String zipcode;

        @NotNull(message = "The geolocation is mandatory", groups = { CreateValidatorGroup.class })
        private Geolocation geolocation;
    }

    @Data
    public static class Geolocation {
        @NotNull(message = "The latitud is mandatory", groups = { CreateValidatorGroup.class })
        @Pattern(regexp = "^-?\\d{1,2}\\.\\d+$", message = "The latitude must be a valid coordinate (e.g., -90.0 to 90.0)")
        private String lat;

        @NotNull(message = "The longitud is mandatory", groups = { CreateValidatorGroup.class })
        @Pattern(regexp = "^-?\\d{1,3}\\.\\d+$", message = "The longitude must be a valid coordinate (e.g., -180.0 to 180.0)")
        @JsonProperty("long")
        private String lon;
    }
}