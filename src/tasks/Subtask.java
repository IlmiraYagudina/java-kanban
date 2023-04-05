package tasks;

import enums.TaskStatus;


public class SubTask extends Task {
    private final int epicId;

    public SubTask(String name, String description, TaskStatus status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "\n" + id + ","
                + taskType + ","
                + name + ","
                + status + ","
                + description + ","
                + epicId;
    }
}
