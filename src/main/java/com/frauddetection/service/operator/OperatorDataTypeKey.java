package com.frauddetection.service.operator;

import com.frauddetection.enums.DataTypeEnum;
import com.frauddetection.enums.OperatorType;

import java.util.Objects;

public class OperatorDataTypeKey {

    private final OperatorType operatorType;
    private final DataTypeEnum dataType;

    public OperatorDataTypeKey(OperatorType operatorType, DataTypeEnum dataType) {
        if (operatorType == null || dataType == null) {
            throw new IllegalArgumentException("OperatorType and DataType cannot be null");
        }
        this.operatorType = operatorType;
        this.dataType = dataType;
    }

    public static OperatorDataTypeKey fromDatabaseValues(String operatorName, String dataTypeName) {
        OperatorType opType = OperatorType.fromDatabaseValue(operatorName);
        DataTypeEnum dtType = DataTypeEnum.fromDatabaseValue(dataTypeName);
        return new OperatorDataTypeKey(opType, dtType);
    }

    public OperatorType getOperatorType() {
        return operatorType;
    }

    public DataTypeEnum getDataType() {
        return dataType;
    }

    public String getOperatorDatabaseValue() {
        return operatorType.getDatabaseValue();
    }

    public String getDataTypeDatabaseValue() {
        return dataType.getDatabaseValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperatorDataTypeKey that = (OperatorDataTypeKey) o;
        return operatorType == that.operatorType && dataType == that.dataType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operatorType, dataType);
    }

    @Override
    public String toString() {
        return "OperatorDataTypeKey{" +
                "operator=" + operatorType +
                ", dataType=" + dataType +
                '}';
    }
}
