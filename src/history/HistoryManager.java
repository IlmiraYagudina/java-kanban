package history;

import taskmanager.ManagerSaveException;
import tasks.Task;
import java.util.List;

public interface HistoryManager {
    void add(Task task); //добавление задачи

    void remove(int id) throws ManagerSaveException; //удалить по айди

    List<Task> getHistory(); //получить истории задач (10 пунктов)

}

