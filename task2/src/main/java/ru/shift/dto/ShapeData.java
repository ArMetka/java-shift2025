package ru.shift.dto;

public class ShapeData {
    private String code;
    private String[] params;

    public ShapeData(String code, String[] params) {
        this.code = code;
        this.params = params;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
