package com.smartstocks.product.dto;

import com.smartstocks.product.models.Gender;
import com.smartstocks.product.validator.ContactNumber;
import com.smartstocks.product.validator.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequestDto {

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @Email(message = "'${validatedValue}' is not well-formed email address")
    private String email;
    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    @Password
    private String password;
    @ContactNumber
    @NotNull(message = "phoneNo cannot be null")
    @NotEmpty(message = "phoneNo cannot be empty")
    private String phoneNo;
    @NotNull(message = "firstName cannot be null")
    @NotEmpty(message = "firstName cannot be empty")
    private String firstName;
    @NotNull(message = "lastName cannot be null")
    @NotEmpty(message = "lastName cannot be empty")
    private String lastName;
    private Date dob;
    private Gender gender;

}
