package com.futurteam.conveyor.models.rows;

import javafx.beans.property.SimpleStringProperty;
import org.jetbrains.annotations.NotNull;

public final class TaskStatusRow {

    @NotNull
    private final SimpleStringProperty number;
    @NotNull
    private final SimpleStringProperty cash;
    @NotNull
    private final SimpleStringProperty processorNumber;

    public TaskStatusRow(@NotNull final String number) {
        this(number, "0", "-");
    }

    public TaskStatusRow(@NotNull final String number,
                         @NotNull final String cash,
                         @NotNull final String processorNumber) {
        this.number = new SimpleStringProperty(number);
        this.cash = new SimpleStringProperty(cash);
        this.processorNumber = new SimpleStringProperty(processorNumber);
    }

    public String getNumber() {
        return this.number.get();
    }

    public void setNumber(@NotNull final String number) {
        this.number.set(number);
    }

    public String getCash() {
        return this.cash.get();
    }

    public void setCash(@NotNull final String cash) {
        this.cash.set(cash);
    }

    public String getProcessorNumber() {
        return this.processorNumber.get();
    }

    public void setProcessorNumber(@NotNull final String processorNumber) {
        this.processorNumber.set(processorNumber);
    }

}
