package history;

import taskmanager.ManagerSaveException;
import tasks.Task;
import java.util.List;

public interface HistoryManager {
    static void getHistory(int id)  {

    }

    void add(Task task); //добавление задачи

    void remove(int id); //удалить по айди

    List<Task> getHistory(); //получить истории задач (10 пунктов)

    void deleteSubtaskById(int id);
}
