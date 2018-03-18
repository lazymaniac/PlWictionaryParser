package com.mindmap.jane.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ParserOperationStatisticsVM {

    private Long duration;

    private Long objectsUpdated;

    private Long currentAmountOfUnits;

    @JsonCreator
    public ParserOperationStatisticsVM() {
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getObjectsUpdated() {
        return objectsUpdated;
    }

    public void setObjectsUpdated(Long objectsUpdated) {
        this.objectsUpdated = objectsUpdated;
    }

    public Long getCurrentAmountOfUnits() {
        return currentAmountOfUnits;
    }

    public void setCurrentAmountOfUnits(Long currentAmountOfUnits) {
        this.currentAmountOfUnits = currentAmountOfUnits;
    }

    @Override
    public String toString() {
        return "ParserOperationStatisticsVM{" +
            "duration=" + duration +
            ", objectsUpdated=" + objectsUpdated +
            ", currentAmountOfUnits=" + currentAmountOfUnits +
            '}';
    }
}
