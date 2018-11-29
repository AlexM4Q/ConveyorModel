package com.futurteam.conveyor.services;

import org.jetbrains.annotations.NotNull;

import java.net.URL;

public final class ResourcesService {

    @NotNull
    public static final URL START_LAYOUT_FXML = ResourcesService.class.getClassLoader().getResource("fxml/start_layout.fxml");
    @NotNull
    public static final URL JOURNAL_RECORD_FXML = ResourcesService.class.getClassLoader().getResource("fxml/journal_record.fxml");

}
