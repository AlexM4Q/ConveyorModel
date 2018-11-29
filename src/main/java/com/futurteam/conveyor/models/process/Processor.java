package com.futurteam.conveyor.models.process;

import com.futurteam.conveyor.models.RandomAdapter;
import com.futurteam.conveyor.models.rows.ProcessorSettingsRow;
import com.futurteam.conveyor.models.rows.ProcessorStatusRow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class Processor {

    @NotNull
    private final Process process;
    @NotNull
    private final ProcessorStatusRow statusRow;
    @NotNull
    private final List<ProcessorTask> processorTaskList;
    @Nullable
    private Processor nextProcessor;
    private final int number;
    private final double processingTime;
    private final double processingTimeDelta;
    private final double memoryIncrement;
    private final double memoryIncrementDelta;
    private boolean isEmployment;
    private boolean isWorkedOnce;
    double totalWorkTime;
    double lastTickTime;
    double startTime;
    double currentTaskDuration;
    int completedTasksCount;
    long totalCash;
    double time;

    public Processor(@NotNull final Process process,
                     @NotNull final ProcessorStatusRow statusRow,
                     @NotNull final ProcessorSettingsRow settingsRow) {
        this(process, statusRow, Integer.valueOf(settingsRow.getProcessorNumber()), Double.valueOf(settingsRow.getProcessingTime()), Double.valueOf(settingsRow.getProcessingTimeDelta()), Double.valueOf(settingsRow.getMemoryIncrement()), Double.valueOf(settingsRow.getMemoryIncrementDelta()));
    }

    private Processor(@NotNull final Process process,
                      @NotNull final ProcessorStatusRow statusRow,
                      final int number,
                      final double processingTime,
                      final double processingTimeDelta,
                      final double memoryIncrement,
                      final double memoryIncrementDelta) {
        this.totalWorkTime = 0.0D;
        this.lastTickTime = 0.0D;
        this.startTime = 0.0D;
        this.currentTaskDuration = 0.0D;
        this.completedTasksCount = 0;
        this.totalCash = 0L;
        this.time = 0.0D;
        this.process = process;
        this.statusRow = statusRow;
        this.number = number;
        this.processingTime = processingTime;
        this.processingTimeDelta = processingTimeDelta;
        this.memoryIncrement = memoryIncrement;
        this.memoryIncrementDelta = memoryIncrementDelta;
        this.processorTaskList = new ArrayList();
    }

    public void process(double time) {
        this.time = time;
        if (!this.processorTaskList.isEmpty()) {
            if (this.isEmployment) {
                double progressTime = time - this.startTime;
                double deltaTime = time - this.lastTickTime;
                if (progressTime > this.currentTaskDuration) {
                    System.out.println("Время: " + time + "\t: Процессор " + this.number + "\t: завершил обработку");
                    this.updateEmployment(false);
                    this.sendTaskNext();
                    this.updateCompleted(++this.completedTasksCount);
                } else {
                    System.out.println("Время: " + time + "\t: Процессор " + this.number + "\t: очередь " + (this.processorTaskList.size() - 1) + ", выполнено " + this.completedTasksCount);
                    this.updateExecution((int) (progressTime * 100.0D / this.currentTaskDuration));
                }

                this.totalWorkTime += deltaTime;
                this.lastTickTime = time;
            } else {
                this.updateEmployment(true);
                this.isWorkedOnce = true;
                this.startTime = time;
                this.lastTickTime = this.startTime;
                this.currentTaskDuration = RandomAdapter.withDelta(this.processingTime, this.processingTimeDelta);
            }
        }

        if (this.isWorkedOnce) {
            this.updateLoad((int) (this.totalWorkTime * 100.0D / time));
        }

    }

    public void addNewTask(@NotNull ProcessorTask processorTask) {
        System.out.println("Время: " + this.time + "\t: Процессор " + this.number + "\t: получил задачу");
        this.processorTaskList.add(processorTask);
        this.updateInCash(processorTask.getWeight());
        this.updateTaskCount(this.processorTaskList.size());
        processorTask.setProcessorNumber(this.number);
    }

    public void setNextProcessor(@Nullable Processor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    private void sendTaskNext() {
        ProcessorTask processorTask = (ProcessorTask) this.processorTaskList.remove(0);
        long cash = (long) RandomAdapter.withDelta(this.memoryIncrement, this.memoryIncrementDelta);
        processorTask.addWeight(cash);
        this.totalCash += cash;
        this.updateOutCash(processorTask.getWeight());
        if (this.nextProcessor == null) {
            this.process.dropOutTask(processorTask);
        } else {
            this.nextProcessor.addNewTask(processorTask);
        }

    }

    private void updateEmployment(boolean isEmployment) {
        this.isEmployment = isEmployment;
        this.statusRow.setEmployment(isEmployment ? "1" : "0");
    }

    private void updateExecution(int execution) {
        this.statusRow.setExecution(execution + "%");
    }

    private void updateCompleted(int completed) {
        this.statusRow.setCompleted(String.valueOf(completed));
    }

    private void updateTaskCount(int tasksCount) {
        this.statusRow.setTasksCount(String.valueOf(tasksCount - 1 < 0 ? 0 : tasksCount - 1));
    }

    private void updateLoad(int load) {
        this.statusRow.setLoad(load + "%");
    }

    private void updateInCash(long inCash) {
        this.statusRow.setInCash(String.valueOf(inCash));
    }

    private void updateOutCash(long outCash) {
        this.statusRow.setOutCash(String.valueOf(outCash));
    }

}
