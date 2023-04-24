package ru.moore.archive.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusInventoryConverter implements AttributeConverter<StatusInventoryEnum, String> {

    @Override
    public String convertToDatabaseColumn(StatusInventoryEnum priority) {
        if (priority == null) {
            return null;
        }
        return priority.getStatus();
    }

    @Override
    public StatusInventoryEnum convertToEntityAttribute(String status) {
        for (StatusInventoryEnum nodeType : StatusInventoryEnum.values()) {
            if (nodeType.getStatus().equals(status)) {
                return nodeType;
            }
        }

        throw new IllegalArgumentException("Unknown database value:" + status);
    }

}
