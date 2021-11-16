package com.smartstocks.product.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        final String[] msg = {"password is less than 8 characters",
                "password does not contain a capital character",
                "password does not contain a small character",
                "password does not contain a number character",
                "password does not contain a special character"};
        boolean[] isAvailable = new  boolean[5];
        if(s.length() >= 8) isAvailable[0] = true;
        for(char c: s.toCharArray()) {
            if(Character.isUpperCase(c)) isAvailable[1] = true;
            else if(Character.isLowerCase(c)) isAvailable[2] = true;
            else if(Character.isDigit(c)) isAvailable[3] = true;
            else isAvailable[4] = true;
        }
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i<isAvailable.length; i++) {
            isAvailable[i] = isAvailable[i] || isAvailable[0];
            if(!isAvailable[i]) buffer.append(msg[i] + "\n ");
        }
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                buffer.toString().strip()).addConstraintViolation();
        return isAvailable[0];
    }
}
