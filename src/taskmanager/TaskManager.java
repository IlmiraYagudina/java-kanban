package taskmanager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager  {
    List<Task> getHistory(); //просмотреть истории задач (последних 10)

    int createTask(Task task); //добавить новую задачу

    int createEpic(Epic epic); //добавить новый эпик

    int createSubtask(Subtask subtask); //добавить новую задачу в эпик

    void deleteTaskById(int id); //удалить задачу по айди

    void deleteEpicById(int id); //удалить эпик по айди

    void deleteSubtaskById(int id); //удалить задачу по айди в эпике

    void deleteAllTasks(); //удалить все задачи (односложные)

    void deleteAllEpics(); //удалить все эпики и в них подзадачи

    void deleteAllSubtasks(); //удалить все подзадачи в эпике

    Task getTaskById(int id); //просмотреть задачу по айди

    Epic getEpicById(int id); //просмотреть эпики по айди

    Subtask getSubtaskById(int id); //просмотреть задачи по айди в эпике

    List<Task> getAllTasks(); //посмотреть все задачи

    List<Epic> getAllEpics(); //посмотреть все эпики

    List<Subtask> getAllSubtasks(); //посмотреть все подзаписи

    List<Subtask> getAllSubtasksByEpicId();

    List<Subtask> getAllSubtasksByEpicId(int id); //посмотреть все записи в эпике

    void updateTask(Task task); //обновить задачи

    void updateEpic(Epic epic); //обновить эпики

    void updateStatusEpic(Epic epic);

    void updateSubtask(Subtask subtask); //обновить подзадачи в эпиках

    void printTasks(); //напечатать задачи

    void printEpics(); //напечатать эпики

    void printSubtasks(); //напечатать подзадачи

    void remove(int i);
}