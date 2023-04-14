package history;

import tasks.Task;
import java.util.List;

public abstract class HistoryManager {
    public abstract void add(Task task);

    public abstract List<Task> getHistory();
}
