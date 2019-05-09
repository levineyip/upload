package com.nbc.upload.library.upload;

import java.util.concurrent.BlockingQueue;

public class TaskExecutor extends Thread {

    private BlockingQueue<ITask> taskQueue;

    private boolean isRunning = true;

    public TaskExecutor(BlockingQueue<ITask> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void quit() {
        isRunning = false;
        interrupt();
    }

    @Override
    public void run() {
        while (isRunning) {
            ITask iTask;
            try {
                iTask = taskQueue.take();
            } catch (InterruptedException e) {
                if (!isRunning) {
                    interrupt();
                    break;
                }
                continue;
            }

            iTask.run();
        }
    }
}
