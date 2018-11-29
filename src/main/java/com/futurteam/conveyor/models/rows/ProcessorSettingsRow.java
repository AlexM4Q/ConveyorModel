package com.futurteam.conveyor.models.rows;

import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;

public final class ProcessorSettingsRow {

    @NotNull
    private final SimpleStringProperty processorNumber;
    @NotNull
    private final SimpleStringProperty processingTime;
    @NotNull
    private final SimpleStringProperty processingTimeDelta;
    @NotNull
    private final SimpleStringProperty memoryIncrement;
    @NotNull
    private final SimpleStringProperty memoryIncrementDelta;

    public ProcessorSettingsRow(@NotNull final String processorNumber) {
        this(processorNumber, "0", "0", "0", "0");
    }

    public ProcessorSettingsRow(@NotNull final String processorNumber,
                                @NotNull final String processingTime,
                                @NotNull final String processingTimeDelta,
                                @NotNull final String memoryIncrement,
                                @NotNull final String memoryIncrementDelta) {
        this.processorNumber = new SimpleStringProperty(processorNumber);
        this.processingTime = new SimpleStringProperty(processingTime);
        this.processingTimeDelta = new SimpleStringProperty(processingTimeDelta);
        this.memoryIncrement = new SimpleStringProperty(memoryIncrement);
        this.memoryIncrementDelta = new SimpleStringProperty(memoryIncrementDelta);
    }

    @NotNull
    public String getProcessorNumber() {
        return this.processorNumber.get();
    }

    public void setProcessorNumber(@NotNull final String processorNumber) {
        this.processorNumber.set(processorNumber);
    }

    @NotNull
    public String getProcessingTime() {
        return this.processingTime.get();
    }

    public void setProcessingTime(@NotNull final String processingTime) {
        this.processingTime.set(processingTime);
    }

    @NotNull
    public String getProcessingTimeDelta() {
        return this.processingTimeDelta.get();
    }

    public void setProcessingTimeDelta(@NotNull final String processingTimeDelta) {
        this.processingTimeDelta.set(processingTimeDelta);
    }

    @NotNull
    public String getMemoryIncrement() {
        return this.memoryIncrement.get();
    }

    public void setMemoryIncrement(@NotNull final String memoryIncrement) {
        this.memoryIncrement.set(memoryIncrement);
    }

    @NotNull
    public String getMemoryIncrementDelta() {
        return this.memoryIncrementDelta.get();
    }

    public void setMemoryIncrementDelta(@NotNull final String memoryIncrementDelta) {
        this.memoryIncrementDelta.set(memoryIncrementDelta);
    }

}
