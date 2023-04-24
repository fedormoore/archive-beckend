package ru.moore.archive.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusInventoryEnum {
    NEW(1L, "Новая"),
    ADD_LOADING(2L, "Дозагрузка"),
    RECYCLING(3L, "Переработка");

    private Long id;
    private String status;

    StatusInventoryEnum(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static StatusInventoryEnum getStatusInventoryEnumFromStatus(String value) {
        for (StatusInventoryEnum sta : StatusInventoryEnum.values()) {
            if (sta.getStatus().equals(value)) {
                return sta;
            }
            if (sta.name().equals(value)) {
                return sta;
            }
            if (sta.getId().equals(value)) {
                return sta;
            }
        }
        return null;
    }

}
