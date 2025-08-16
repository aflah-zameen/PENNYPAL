package com.application.pennypal.domain.user.validator;

import com.application.pennypal.domain.user.exception.validation.InvalidURLDomainException;
import com.application.pennypal.domain.user.exception.validation.MissingProfileURLDomainException;

import java.net.MalformedURLException;
import java.net.URL;

public class URLValidator {
    public static void validate(String url){
        if(url != null){
            try {
                new URL(url);
            } catch (MalformedURLException e) {
                throw new InvalidURLDomainException("URL is invalid");
            }
        }

    }
}
