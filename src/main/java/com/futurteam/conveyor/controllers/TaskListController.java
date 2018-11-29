package com.futurteam.conveyor.controllers;

import com.futurteam.conveyor.models.rows.TaskStatusRow;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.NotNull;

public final class TaskListController {

    @FXML
    private TableView<TaskStatusRow> status_TV;

    public void addTask(@NotNull final TaskStatusRow taskStatusRow) {
        this.status_TV.getItems().add(taskStatusRow);
    }

    public void clear() {
        this.status_TV.getItems().clear();
    }

}
