package com.chitkara.bfhl.model;

public class ResponseDTO {
    private boolean is_success;
    private String official_email;
    private Object data;
    private String error;

    public ResponseDTO(boolean is_success, String official_email, Object data, String error) {
        this.is_success = is_success;
        this.official_email = official_email;
        this.data = data;
        this.error = error;
    }

    public boolean isIs_success() { return is_success; }
    public String getOfficial_email() { return official_email; }
    public Object getData() { return data; }
    public String getError() { return error; }
}
