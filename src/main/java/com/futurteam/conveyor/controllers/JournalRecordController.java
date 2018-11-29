package com.futurteam.conveyor.controllers;

import com.futurteam.conveyor.models.rows.ProcessorStatusRow;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class JournalRecordController {

    @FXML
    private StatusTableController status_TVController;
    @FXML
    private TitledPane root_TP;
    @FXML
    private TableView<ProcessorStatusRow> status_TV;

    public void setData(@NotNull final String title, @NotNull final ObservableList<ProcessorStatusRow> data) {
        this.root_TP.setText(title);

        for (@NotNull final ProcessorStatusRow statusRow : data) {
            this.status_TV.getItems().add(statusRow.clone());
        }
    }

    public void setFullData(@NotNull final Supplier<Accordion> fullData) {
        this.status_TVController.setFullData(fullData);
    }

}
