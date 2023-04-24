package ru.moore.archive.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StatusRecordConverter implements AttributeConverter<StatusRecordEnum, String> {

    @Override
    public String convertToDatabaseColumn(StatusRecordEnum priority) {
        if (priority == null) {
            return null;
        }
        return priority.getStatus();
    }

    @Override
    public StatusRecordEnum convertToEntityAttribute(String status) {
        for (StatusRecordEnum nodeType : StatusRecordEnum.values()) {
            if (nodeType.getStatus().equals(status)) {
                return nodeType;
            }
        }

        throw new IllegalArgumentException("Unknown database value:" + status);
    }

}
