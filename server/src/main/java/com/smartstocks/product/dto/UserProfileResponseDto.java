package com.smartstocks.product.dto;

import com.smartstocks.product.models.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileResponseDto {

    private String email;
    private String phoneNo;
    private String firstName;
    private String lastName;
    private Date dob;
    private Gender gender;
    private double profit;
}
