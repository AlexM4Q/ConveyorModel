package com.futurteam.conveyor.controllers;

import com.futurteam.conveyor.models.Updater;
import com.futurteam.conveyor.models.rows.ProcessorStatusRow;
import com.futurteam.conveyor.services.ResourcesService;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class RightBarController {

    private static final int TABLE_UPDATE_TIME = 100;
    @NotNull
    private final List<Runnable> onStartListeners = new ArrayList();
    @NotNull
    private final List<Runnable> onStopListeners = new ArrayList();
    @FXML
    private StatusTableController status_TVController;
    @FXML
    private TableView<ProcessorStatusRow> status_TV;
    @FXML
    private Label experimentTime_L;
    @FXML
    private Accordion journalLog_A;
    private Updater tableUpdater;

    @FXML
    public void initialize() {
        this.status_TVController.setFullData(() -> this.journalLog_A);
        this.tableUpdater = new Updater(100L) {
            public void run() {
                Platform.runLater(() -> RightBarController.this.status_TV.refresh());
            }
        };
    }

    @FXML
    private void start_B_action() {
        this.onStartListeners.forEach(Runnable::run);
        this.tableUpdater.start();
    }

    @FXML
    private void stop_B_action() {
        this.onStopListeners.forEach(Runnable::run);
        this.tableUpdater.stop();
        this.status_TV.getItems().clear();
        this.journalLog_A.getPanes().clear();
        this.experimentTime_L.setText("0");
    }

    public void addOnStartListener(@NotNull Runnable listener) {
        this.onStartListeners.add(listener);
    }

    public void addOnStopListener(@NotNull Runnable listener) {
        this.onStopListeners.add(listener);
    }

    @NotNull
    public ObservableList<ProcessorStatusRow> getStatus() {
        return this.status_TV.getItems();
    }

    public void setProcess(@NotNull final com.futurteam.conveyor.models.process.Process process) {
        process.addProcessStoppedListener(() -> this.tableUpdater.stop());
        process.setOnTimeUpdateListener((currentProcessTime) -> {
            String time = String.valueOf(currentProcessTime);
            Platform.runLater(() -> this.experimentTime_L.setText(time));
        });
        process.setOnReportTimeAchievedListener(this::addNewRecord);
    }

    private void addNewRecord(double time) {
        @NotNull final FXMLLoader record = new FXMLLoader(ResourcesService.JOURNAL_RECORD_FXML);

        @NotNull final TitledPane load;
        try {
            load = record.load();
        } catch (IOException var7) {
            return;
        }

        @NotNull final String formatTime = String.valueOf(time);
        @NotNull final JournalRecordController controller = record.getController();
        controller.setData(formatTime, this.status_TV.getItems());
        controller.setFullData(() -> this.journalLog_A);
        Platform.runLater(() -> this.journalLog_A.getPanes().add(load));
    }
}
