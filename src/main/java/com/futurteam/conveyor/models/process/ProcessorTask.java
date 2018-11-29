package com.futurteam.conveyor.models.process;

import com.futurteam.conveyor.models.rows.TaskStatusRow;
import org.jetbrains.annotations.NotNull;

public final class ProcessorTask {

    @NotNull
    private final TaskStatusRow taskStatusRow;
    private long weight;

    public ProcessorTask(final int number) {
        this(new TaskStatusRow(String.valueOf(number)));
    }

    public ProcessorTask(@NotNull final TaskStatusRow taskStatusRow) {
        this.weight = 1L;
        this.taskStatusRow = taskStatusRow;
    }

    @NotNull
    public final TaskStatusRow getTaskStatusRow() {
        return this.taskStatusRow;
    }

    public long getWeight() {
        return this.weight;
    }

    public void addWeight(final long weight) {
        this.weight += weight;
        this.taskStatusRow.setCash(String.valueOf(this.weight));
    }

    public void setProcessorNumber(final int processorNumber) {
        if (processorNumber == -1) {
            this.taskStatusRow.setProcessorNumber("+");
        } else {
            this.taskStatusRow.setProcessorNumber(String.valueOf(processorNumber));
        }
    }

}
