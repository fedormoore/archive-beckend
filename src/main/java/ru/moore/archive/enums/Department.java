package ru.moore.archive.enums;

public enum Department {
    ACQUISITION("Отдел комплектования"), STORAGE("Заведующий хранилищем");

    private String code;

    private Department(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
