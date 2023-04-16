package taskmanager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getHistory();

    int createTask(Task task); //добавить новую задачу

    int createEpic(Epic epic);

    int createSubtask(Subtask subtask);

    void deleteTaskById(int id); //удалить задачу оп айди

    void deleteEpicById(int id);

    void deleteSubtaskById(int id);

    void deleteAllTasks(); //удалить все задачи

    void deleteAllEpics();

    void deleteAllSubtasks();

    Task getTaskById(int id); //просмотреть задачу по айди

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    List<Task> getAllTasks(); //посмотреть все такси

    List<Object> getAllEpics();

    List<Object> getAllSubtasks();

    List<Object> getAllSubtasksByEpicId(int id);

    void updateTask(Task task); //обновить задачи

    void updateEpic(Epic epic);

//    void updateStatusEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void printTasks(); //напечатать задачу

    void printEpics();

    void printSubtasks();
}