package com.mindmap.jane.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UpdateDatabaseResultDTO {

    private ParserOperationStatusEnum operationStatusEnum;

    private String operation;

    private String comment;

    private ParserOperationStatisticsVM operationStatistics;

    @JsonCreator
    public UpdateDatabaseResultDTO() {
    }

    public ParserOperationStatusEnum getOperationStatusEnum() {
        return operationStatusEnum;
    }

    public void setOperationStatusEnum(ParserOperationStatusEnum operationStatusEnum) {
        this.operationStatusEnum = operationStatusEnum;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ParserOperationStatisticsVM getOperationStatistics() {
        return operationStatistics;
    }

    public void setOperationStatistics(ParserOperationStatisticsVM operationStatistics) {
        this.operationStatistics = operationStatistics;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UpdateDatabaseResultDTO{");
        sb.append("operationStatusEnum=").append(operationStatusEnum);
        sb.append(", operation='").append(operation).append('\'');
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", operationStatistics=").append(operationStatistics);
        sb.append('}');
        return sb.toString();
    }
}
