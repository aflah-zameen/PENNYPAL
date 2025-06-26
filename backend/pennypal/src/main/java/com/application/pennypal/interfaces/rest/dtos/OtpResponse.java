package com.application.pennypal.interfaces.rest.dtos;

public class OtpResponse extends ApiResponse<OtpDTO>{
    public OtpResponse(boolean success,OtpDTO otpDTO,String message){
        super(success,otpDTO,message);
    }
}
