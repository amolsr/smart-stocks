package com.smartstocks.product.dto;

import com.smartstocks.product.models.Gender;
import com.smartstocks.product.validator.ContactNumber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignedInUserDto {
    @ContactNumber
    @NotEmpty(message = "phoneNo cannot be empty")
    private String phoneNo;
    @NotEmpty(message = "firstName cannot be empty")
    private String firstName;
    @NotEmpty(message = "lastName cannot be empty")
    private String lastName;
    private Date dob;
    private Gender gender;
}
