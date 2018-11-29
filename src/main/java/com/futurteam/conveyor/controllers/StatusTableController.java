package com.futurteam.conveyor.controllers;

import com.futurteam.conveyor.models.rows.ProcessorStatusRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public final class StatusTableController {
    @FXML
    private TableView<ProcessorStatusRow> table_TV;
    @Nullable
    private Supplier<Accordion> fullData;

    @Nullable
    private double[][] proc(int row, int column) {
        if (this.fullData == null) {
            return null;
        } else {
            Accordion accordion = this.fullData.get();
            ObservableList<TitledPane> panes = accordion.getPanes();
            double[][] data = new double[panes.size()][2];

            for (int i = 0; i < panes.size(); ++i) {
                @NotNull final TitledPane pane = panes.get(i);
                data[i][0] = Double.valueOf(pane.getText());

                @NotNull final TableView<ProcessorStatusRow> tableView = (TableView) pane.getContent();
                String valueByColumn = getValueByColumn(tableView.getItems().get(row), column);
                data[i][1] = Double.valueOf(valueByColumn);
            }

            return data;
        }
    }

    public void setFullData(@NotNull Supplier<Accordion> fullData) {
        this.fullData = fullData;
    }

    private void createChart(@NotNull double[][] data) {
        @NotNull final Stage stage = new Stage();
        @NotNull final LineChart<Number, Number> chart = new LineChart<>(new NumberAxis(), new NumberAxis());
        @NotNull final ObservableList<XYChart.Data<Number, Number>> seriesData = FXCollections.observableArrayList();

        for (@NotNull final double[] datum : data) {
            seriesData.add(new XYChart.Data<>(datum[0], datum[1]));
        }

        XYChart.Series<Number, Number> series = new XYChart.Series<>(seriesData);
        chart.getData().add(series);
        chart.setLegendVisible(false);
        chart.setCreateSymbols(false);
        stage.setScene(new Scene(chart));
        stage.show();
    }

    @FXML
    private void table_TV_mouseClick(@NotNull MouseEvent event) {
        if (event.getClickCount() == 2) {
            ObservableList<TablePosition> selectedCells = this.table_TV.getSelectionModel().getSelectedCells();
            if (!selectedCells.isEmpty()) {
                TablePosition tablePosition = selectedCells.get(0);
                double[][] data = this.proc(tablePosition.getRow(), tablePosition.getColumn());
                if (data != null) {
                    this.createChart(data);
                }
            }
        }
    }

    @NotNull
    private static String getValueByColumn(@NotNull ProcessorStatusRow rowData, int column) {
        switch (column) {
            case 0:
                return rowData.getProcessorNumber();
            case 1:
                return rowData.getEmployment();
            case 2:
                return rowData.getExecution().replaceAll("%", "");
            case 3:
                return rowData.getCompleted();
            case 4:
                return rowData.getTasksCount();
            case 5:
                return rowData.getLoad().replaceAll("%", "");
            case 6:
                return rowData.getInCash();
            case 7:
                return rowData.getOutCash();
            default:
                throw new IllegalArgumentException("Cannot case " + column);
        }
    }
}
