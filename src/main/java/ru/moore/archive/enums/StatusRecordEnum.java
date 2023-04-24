package ru.moore.archive.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusRecordEnum {
    DRAFT(1L, "Черновик", "ACQUISITION, DIRECTOR", "ACQUISITION"),
    IN_STORAGE(2L, "Передано в хранилище", "ACQUISITION, STORAGE, DIRECTOR", "ACQUISITION"),
    ACCEPTED_FOR_STORAGE(3L, "Принято на хранение", "ACQUISITION, DIRECTOR", "ACQUISITION"),

    REFINE_STORAGE(4L, "На доработку от заведующего хранилищем", "ACQUISITION, STORAGE, DIRECTOR", "STORAGE"),
    IN_SCANNING(5L, "Передано на сканирование", "ACQUISITION, STORAGE, SCANNING, DIRECTOR", "STORAGE"),

    REFINE_SCANNING(6L, "На доработку от отдела сканирования", "ACQUISITION, STORAGE, SCANNING, DIRECTOR", "SCANNING"),
    IS_SCANNING(7L, "Отсканировано", "ACQUISITION, STORAGE, SCANNING, DIRECTOR", "SCANNING"),
    IN_LOADING(8L, "Передано на загрузку", "ACQUISITION, STORAGE, SCANNING, DIRECTOR, LOADING", "SCANNING"),

    REFINE_LOADING(9L, "На доработку от отдела загрузки", "ACQUISITION, STORAGE, SCANNING, LOADING, DIRECTOR", "LOADING"),
    IS_DIRECTOR(10L, "Загруженно", "ACQUISITION, STORAGE, SCANNING, LOADING, DIRECTOR", "LOADING"),

    REFINE_DIRECTOR(11L, "На доработку от проверяющего", "ACQUISITION, STORAGE, SCANNING, LOADING, DIRECTOR", "DIRECTOR"),
    IS_CHECKED(12L, "Проверенно", "ACQUISITION, STORAGE, SCANNING, LOADING, DIRECTOR", "DIRECTOR");

    private Long id;
    private String status;
    private String visible;
    private String selection;

    StatusRecordEnum(Long id, String status, String visible, String selection) {
        this.id = id;
        this.status = status;
        this.visible = visible;
        this.selection = selection;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getVisible() {
        return visible;
    }

    public String getSelection() {
        return selection;
    }

    @JsonCreator
    public static StatusRecordEnum getStatusEnumFromStatus(String value) {
        for (StatusRecordEnum sta : StatusRecordEnum.values()) {
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
