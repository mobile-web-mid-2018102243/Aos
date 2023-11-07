package com.chunbae.moweb_android;

public class SignUpDTO {
    private Boolean Success;

    // Constructor
    public SignUpDTO(Boolean success) {
        this.Success = success;
    }

    // Getter and Setter
    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        this.Success = success;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "SignUpDTO{" +
                "Success=" + Success +
                '}';
    }
}
