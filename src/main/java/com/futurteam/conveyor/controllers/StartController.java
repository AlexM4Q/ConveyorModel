package com.futurteam.conveyor.controllers;

import javafx.fxml.FXML;
import org.jetbrains.annotations.Nullable;

public final class StartController {
    @FXML
    private LeftBarController leftBarLayoutController;
    @FXML
    private RightBarController rightBarLayoutController;
    @FXML
    private TaskListController tasksListController;
    @Nullable
    private com.futurteam.conveyor.models.process.Process process;

    @FXML
    public void initialize() {
        this.rightBarLayoutController.addOnStartListener(this::updateProcess);
        this.rightBarLayoutController.addOnStopListener(() -> {
            this.tasksListController.clear();
            if (this.process != null) {
                this.process.close();
                this.process = null;
            }

        });
    }

    private void updateProcess() {
        this.process = new com.futurteam.conveyor.models.process.Process(this.leftBarLayoutController.getIncrement(), this.leftBarLayoutController.getExperimentTime(), this.leftBarLayoutController.getTaskInterval(), this.leftBarLayoutController.getTaskIntervalDelta(), this.leftBarLayoutController.getReportInterval(), this.leftBarLayoutController.getSettings(), this.rightBarLayoutController.getStatus());
        this.rightBarLayoutController.setProcess(this.process);
        this.process.setOnNewTaskCreatedListener((taskStatusRow) -> this.tasksListController.addTask(taskStatusRow.getTaskStatusRow()));
    }
}
