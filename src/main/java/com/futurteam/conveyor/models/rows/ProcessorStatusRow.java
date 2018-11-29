package com.futurteam.conveyor.models.rows;

import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;

public final class ProcessorStatusRow {

    @NotNull
    private final SimpleStringProperty processorNumber;
    @NotNull
    private final SimpleStringProperty employment;
    @NotNull
    private final SimpleStringProperty execution;
    @NotNull
    private final SimpleStringProperty completed;
    @NotNull
    private final SimpleStringProperty tasksCount;
    @NotNull
    private final SimpleStringProperty load;
    @NotNull
    private final SimpleStringProperty inCash;
    @NotNull
    private final SimpleStringProperty outCash;

    public ProcessorStatusRow(@NotNull final String processorNumber) {
        this(processorNumber, "0", "0%", "0", "0", "0%", "0", "0");
    }

    public ProcessorStatusRow(@NotNull final String processorNumber,
                              @NotNull final String employment,
                              @NotNull final String execution,
                              @NotNull final String completed,
                              @NotNull final String tasksCount,
                              @NotNull final String load,
                              @NotNull final String inCash,
                              @NotNull final String outCash) {
        this.processorNumber = new SimpleStringProperty(processorNumber);
        this.employment = new SimpleStringProperty(employment);
        this.execution = new SimpleStringProperty(execution);
        this.completed = new SimpleStringProperty(completed);
        this.tasksCount = new SimpleStringProperty(tasksCount);
        this.load = new SimpleStringProperty(load);
        this.inCash = new SimpleStringProperty(inCash);
        this.outCash = new SimpleStringProperty(outCash);
    }

    @NotNull
    public String getProcessorNumber() {
        return this.processorNumber.get();
    }

    public void setProcessorNumber(@NotNull final String processorNumber) {
        this.processorNumber.set(processorNumber);
    }

    @NotNull
    public String getEmployment() {
        return this.employment.get();
    }

    public void setEmployment(@NotNull final String employment) {
        this.employment.set(employment);
    }

    @NotNull
    public String getExecution() {
        return this.execution.get();
    }

    public void setExecution(@NotNull final String execution) {
        this.execution.set(execution);
    }

    @NotNull
    public String getCompleted() {
        return this.completed.get();
    }

    public void setCompleted(@NotNull final String completed) {
        this.completed.set(completed);
    }

    @NotNull
    public String getTasksCount() {
        return this.tasksCount.get();
    }

    public void setTasksCount(@NotNull final String tasksCount) {
        this.tasksCount.set(tasksCount);
    }

    @NotNull
    public String getLoad() {
        return this.load.get();
    }

    public void setLoad(@NotNull final String load) {
        this.load.set(load);
    }

    @NotNull
    public String getInCash() {
        return this.inCash.get();
    }

    public void setInCash(@NotNull final String inCash) {
        this.inCash.set(inCash);
    }

    @NotNull
    public String getOutCash() {
        return this.outCash.get();
    }

    public void setOutCash(@NotNull final String outCash) {
        this.outCash.set(outCash);
    }

    @NotNull
    public ProcessorStatusRow clone() {
        return new ProcessorStatusRow(this.processorNumber.get(), this.employment.get(), this.execution.get(), this.completed.get(), this.tasksCount.get(), this.load.get(), this.inCash.get(), this.outCash.get());
    }

}
