package com.futurteam.conveyor.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Updater implements Runnable {

    private final long duration;
    private final long firstDelay;
    @NotNull
    private final Timer timer;
    @Nullable
    private TimerTask task;

    public Updater(long duration) {
        this(duration, 0L);
    }

    public Updater(long duration, long firstDelay) {
        this.duration = duration;
        this.firstDelay = firstDelay;
        this.timer = new Timer();
    }

    public void start() {
        if (this.firstDelay == 0L) {
            this.timer.scheduleAtFixedRate(this.task = new TimerTask() {
                public void run() {
                    Updater.this.run();
                }
            }, 0L, this.duration);
        } else {
            this.timer.scheduleAtFixedRate(this.task = new TimerTask() {
                public void run() {
                    Updater.this.run();
                }
            }, Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + this.firstDelay)), this.duration);
        }
    }

    public void stop() {
        if (this.task != null) {
            this.task.cancel();
        }
    }

}
