package com.chunbae.moweb_android;

public class EditContentDTO {
    private boolean success;

    public EditContentDTO(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
