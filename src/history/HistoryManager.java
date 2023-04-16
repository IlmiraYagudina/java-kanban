package history;

import tasks.Task;
import java.util.List;

public interface HistoryManager {
    void add(Task task); //добавление задачи

    List<Task> getHistory(); //получить истории задач (10 пуктов)
}
