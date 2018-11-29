package com.futurteam.conveyor.models.listeners;

import com.futurteam.conveyor.models.process.ProcessorTask;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface OnNewTaskCreatedListener {

    void onNewTaskCreated(@NotNull final ProcessorTask var1);

}
