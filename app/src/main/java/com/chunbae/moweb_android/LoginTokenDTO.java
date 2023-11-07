package com.chunbae.moweb_android;

public class LoginTokenDTO {

    private ResultToken result;

    // Constructor
    public LoginTokenDTO(ResultToken result) {
        this.result = result;
    }

    // Getter and Setter
    public ResultToken getResult() {
        return result;
    }

    public void setResult(ResultToken result) {
        this.result = result;
    }

    // Inner class for ResultToken
    public static class ResultToken {
        private String token;

        // Constructor
        public ResultToken(String token) {
            this.token = token;
        }

        // Getter and Setter
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
