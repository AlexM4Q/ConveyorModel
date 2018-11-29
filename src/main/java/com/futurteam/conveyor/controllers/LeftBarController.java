package com.futurteam.conveyor.controllers;

import com.futurteam.conveyor.constants.IncrementAccuracyType;
import com.futurteam.conveyor.constants.IncrementType;
import com.futurteam.conveyor.models.rows.ProcessorSettingsRow;
import com.futurteam.conveyor.utils.FormatterUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public final class LeftBarController implements Initializable {

    @FXML
    private TableView<ProcessorSettingsRow> settings_TV;
    @FXML
    private TextField experimentTime_TF;
    @FXML
    private TextField tasksInterval_TF;
    @FXML
    private TextField tasksIntervalDelta_TF;
    @FXML
    private TextField reportInterval_TF;
    @FXML
    private TextField processorsCount_TF;
    @FXML
    private TextField timeIncrementAccuracy_TF;
    @FXML
    private TextField timeIncrement_TF;
    @FXML
    private Label timeIncrement_L;
    @FXML
    private Label timeIncrementAccuracy_L;
    @FXML
    private RadioButton timeIncrementDecimalPartType_RB;
    @FXML
    private RadioButton timeIncrementDecimalPercentType_RB;

    @NotNull
    private IncrementType incrementType;
    @NotNull
    private IncrementAccuracyType incrementAccuracyType;

    public LeftBarController() {
        this.incrementType = IncrementType.INPUT;
        this.incrementAccuracyType = IncrementAccuracyType.FACTOR;
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        this.prepareProcessorsCount_TF();
        this.prepareSettings_TV();
        FormatterUtils.applyIntegerFormat(this.processorsCount_TF);
        FormatterUtils.applyDoubleFormat(this.experimentTime_TF);
        FormatterUtils.applyDoubleFormat(this.reportInterval_TF);
        FormatterUtils.applyDoubleFormat(this.tasksInterval_TF);
        FormatterUtils.applyDoubleFormat(this.timeIncrementAccuracy_TF);
        FormatterUtils.applyDoubleFormat(this.timeIncrement_TF);
        this.timeIncrementAccuracy_L.setDisable(true);
        this.timeIncrementAccuracy_TF.setDisable(true);
        this.timeIncrementDecimalPartType_RB.setDisable(true);
        this.timeIncrementDecimalPercentType_RB.setDisable(true);
    }

    private void prepareProcessorsCount_TF() {
        this.processorsCount_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            int n;
            try {
                n = Integer.valueOf(newValue);
            } catch (NumberFormatException var8) {
                return;
            }

            ObservableList<ProcessorSettingsRow> items = this.settings_TV.getItems();
            int delta = n - items.size();
            int i;
            if (delta > 0) {
                for (i = 0; i < delta; ++i) {
                    items.add(new ProcessorSettingsRow(String.valueOf(items.size() + 1)));
                }
            } else {
                for (i = delta; i < 0; ++i) {
                    items.remove(items.size() - 1);
                }
            }

            this.prepareSettings_TV();
        });
    }

    @SuppressWarnings("unchecked")
    private void prepareSettings_TV() {
        ObservableList<TableColumn<ProcessorSettingsRow, ?>> columns = this.settings_TV.getColumns();
        List<TableColumn<ProcessorSettingsRow, String>> castedColumns = columns.stream().map((column) -> (TableColumn<ProcessorSettingsRow, String>) column).collect(Collectors.toList());
        castedColumns.get(1).setCellFactory(TextFieldTableCell.forTableColumn());
        castedColumns.get(1).setOnEditCommit((t) -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setProcessingTime(t.getNewValue()));
        castedColumns.get(2).setCellFactory(TextFieldTableCell.forTableColumn());
        castedColumns.get(2).setOnEditCommit((t) -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setProcessingTimeDelta(t.getNewValue()));
        castedColumns.get(3).setCellFactory(TextFieldTableCell.forTableColumn());
        castedColumns.get(3).setOnEditCommit((t) -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setMemoryIncrement(t.getNewValue()));
        castedColumns.get(4).setCellFactory(TextFieldTableCell.forTableColumn());
        castedColumns.get(4).setOnEditCommit((t) -> t.getTableView().getItems().get(t.getTablePosition().getRow()).setMemoryIncrementDelta(t.getNewValue()));
    }

    @NotNull
    public ObservableList<ProcessorSettingsRow> getSettings() {
        return this.settings_TV.getItems();
    }

    public double getExperimentTime() {
        try {
            return Double.valueOf(this.experimentTime_TF.getText());
        } catch (NumberFormatException var2) {
            return 0;
        }
    }

    public double getTaskInterval() {
        try {
            return Double.valueOf(this.tasksInterval_TF.getText());
        } catch (NumberFormatException var2) {
            return 1.7976931348623157E308D;
        }
    }

    public double getTaskIntervalDelta() {
        try {
            return Double.valueOf(this.tasksIntervalDelta_TF.getText());
        } catch (NumberFormatException var2) {
            return Double.MAX_VALUE;
        }
    }

    public double getReportInterval() {
        try {
            return Double.valueOf(this.reportInterval_TF.getText());
        } catch (NumberFormatException var2) {
            return Double.MAX_VALUE;
        }
    }

    public double getIncrement() {
        switch (this.incrementType) {
            case INPUT:
                return Double.valueOf(this.timeIncrement_TF.getText());
            case CALCULATE:
                return this.calculateIncrement() * this.getAccuracy();
            default:
                throw new IllegalArgumentException(this.incrementType.name());
        }
    }

    private double getAccuracy() {
        switch (this.incrementAccuracyType) {
            case FACTOR:
                return Double.valueOf(this.timeIncrementAccuracy_TF.getText());
            case PERCENT:
                return Double.valueOf(this.timeIncrementAccuracy_TF.getText()) / 100.0D;
            default:
                throw new IllegalArgumentException(this.incrementAccuracyType.name());
        }
    }

    private double calculateIncrement() {
        double minIncrement = Double.MAX_VALUE;

        for (@NotNull final ProcessorSettingsRow processorSettingsRow : this.settings_TV.getItems()) {
            final double increment = Double.valueOf(processorSettingsRow.getProcessingTime()) - Double.valueOf(processorSettingsRow.getProcessingTimeDelta());
            if (minIncrement > increment) {
                minIncrement = increment;
            }
        }

        return minIncrement;
    }

    @FXML
    private void inputTimeIncrementType_action() {
        this.incrementType = IncrementType.INPUT;
        this.timeIncrement_L.setDisable(false);
        this.timeIncrement_TF.setDisable(false);
        this.timeIncrementAccuracy_L.setDisable(true);
        this.timeIncrementAccuracy_TF.setDisable(true);
        this.timeIncrementDecimalPartType_RB.setDisable(true);
        this.timeIncrementDecimalPercentType_RB.setDisable(true);
    }

    @FXML
    private void processTimeIncrementType_action() {
        this.incrementType = IncrementType.CALCULATE;
        this.timeIncrement_L.setDisable(true);
        this.timeIncrement_TF.setDisable(true);
        this.timeIncrementAccuracy_L.setDisable(false);
        this.timeIncrementAccuracy_TF.setDisable(false);
        this.timeIncrementDecimalPartType_RB.setDisable(false);
        this.timeIncrementDecimalPercentType_RB.setDisable(false);
    }

    @FXML
    private void timeIncrementDecimalPartType_action() {
        this.incrementAccuracyType = IncrementAccuracyType.FACTOR;
    }

    @FXML
    private void timeIncrementDecimalPercentType_action() {
        this.incrementAccuracyType = IncrementAccuracyType.PERCENT;
    }

}
