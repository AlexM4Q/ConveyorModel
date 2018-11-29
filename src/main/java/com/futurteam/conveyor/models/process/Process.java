package com.futurteam.conveyor.models.process;

import com.futurteam.conveyor.models.RandomAdapter;
import com.futurteam.conveyor.models.listeners.OnNewTaskCreatedListener;
import com.futurteam.conveyor.models.listeners.OnReportTimeAchievedListener;
import com.futurteam.conveyor.models.listeners.OnTimeUpdateListener;
import com.futurteam.conveyor.models.rows.ProcessorSettingsRow;
import com.futurteam.conveyor.models.rows.ProcessorStatusRow;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class Process implements Runnable, AutoCloseable {

    private final double incrementTime;
    private final double experimentTime;
    private final double taskInterval;
    private final double taskIntervalDelta;
    private final double reportInterval;

    @NotNull
    private final List<Runnable> onProcessStoppedListeners;
    @NotNull
    private final LinkedList<Processor> processors;
    @Nullable
    private OnTimeUpdateListener onTimeUpdateListener;
    @Nullable
    private OnReportTimeAchievedListener onReportTimeAchievedListener;
    @Nullable
    private OnNewTaskCreatedListener onNewTaskCreatedListener;

    private int tasksCount = 0;

    public Process(final double incrementTime,
                   final double experimentTime,
                   final double taskInterval,
                   final double taskIntervalDelta,
                   final double reportInterval,
                   @NotNull final List<ProcessorSettingsRow> settings,
                   @NotNull final ObservableList<ProcessorStatusRow> processorStatusRows) {
        this.incrementTime = incrementTime;
        this.experimentTime = experimentTime;
        this.taskInterval = taskInterval;
        this.taskIntervalDelta = taskIntervalDelta;
        this.reportInterval = reportInterval;
        this.onProcessStoppedListeners = new ArrayList<>();
        this.processors = new LinkedList<>();

        for (ProcessorSettingsRow setting : settings) {
            @NotNull final ProcessorStatusRow processorStatusRow = new ProcessorStatusRow(String.valueOf(processorStatusRows.size() + 1));
            @NotNull final Processor processor = new Processor(this, processorStatusRow, setting);

            if (!processors.isEmpty()) {
                processors.getLast().setNextProcessor(processor);
            }

            processors.add(processor);
            processorStatusRows.add(processorStatusRow);
        }

        @NotNull final Thread process = new Thread(this);
        process.setDaemon(true);
        process.start();
    }

    public void run() {
        double timeToNextTask = RandomAdapter.withDelta(this.taskInterval, this.taskIntervalDelta);
        double timeToNextReport = this.reportInterval;

        for (double time = 0.0D; time <= this.experimentTime; time += this.incrementTime) {
            this.fireOnTimeUpdate(time);

            for (@NotNull final Processor processor : this.processors) {
                processor.process(time);
            }

            if (timeToNextTask <= 0.0D) {
                timeToNextTask = RandomAdapter.withDelta(this.taskInterval, this.taskIntervalDelta);
                ProcessorTask processorTask = new ProcessorTask(++this.tasksCount);
                this.processors.getFirst().addNewTask(processorTask);
                this.fireOnNewTaskCreated(processorTask);
            }

            if (timeToNextReport <= 0.0D) {
                timeToNextReport = this.reportInterval;
                this.fireReportTimeAchieved(time);
            }

            timeToNextTask -= this.incrementTime;
            timeToNextReport -= this.incrementTime;
        }
    }

    public void close() {
        this.onProcessStoppedListeners.forEach(Runnable::run);
        System.out.println("Конец процесса");
    }

    public void addProcessStoppedListener(@NotNull final Runnable listener) {
        this.onProcessStoppedListeners.add(listener);
    }

    public void dropOutTask(@NotNull final ProcessorTask processorTask) {
        processorTask.setProcessorNumber(-1);
    }

    private void fireOnTimeUpdate(final double currentProcessTime) {
        if (this.onTimeUpdateListener != null) {
            this.onTimeUpdateListener.onTimeUpdate(currentProcessTime);
        }
    }

    public void setOnTimeUpdateListener(@Nullable final OnTimeUpdateListener onTimeUpdateListener) {
        this.onTimeUpdateListener = onTimeUpdateListener;
    }

    private void fireReportTimeAchieved(final double currentProcessTime) {
        if (this.onReportTimeAchievedListener != null) {
            this.onReportTimeAchievedListener.onReportTimeAchieved(currentProcessTime);
        }
    }

    public void setOnReportTimeAchievedListener(@Nullable final OnReportTimeAchievedListener onReportTimeAchievedListener) {
        this.onReportTimeAchievedListener = onReportTimeAchievedListener;
    }

    private void fireOnNewTaskCreated(@NotNull final ProcessorTask taskStatusRow) {
        if (this.onNewTaskCreatedListener != null) {
            this.onNewTaskCreatedListener.onNewTaskCreated(taskStatusRow);
        }
    }

    public void setOnNewTaskCreatedListener(@Nullable final OnNewTaskCreatedListener onNewTaskCreatedListener) {
        this.onNewTaskCreatedListener = onNewTaskCreatedListener;
    }

}
