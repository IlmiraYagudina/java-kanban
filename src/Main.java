
import enums.TaskStatus;

import taskmanager.ManagerSaveException;
import taskmanager.Managers;
import taskmanager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;


public class Main {

    public static void main(String[] args) throws ManagerSaveException {

        TaskManager taskManager = Managers.getInMemoryTaskManager();

        Task firstTask = new Task("Сделать дизайн-проект", "Ремонт квартиры", TaskStatus.NEW);
        taskManager.createTask(firstTask);
        Task secondTask = new Task("Проект", "Сообщество IT", TaskStatus.NEW);
        taskManager.createTask(secondTask);

        Epic firstEpic = new Epic("Пригласить друзей", "ДР", TaskStatus.NEW);
        taskManager.createEpic(firstEpic);

        Subtask firstSubtask = new Subtask("Сделать проект", "Работа", TaskStatus.NEW, firstEpic.getId());
        taskManager.createSubtask(firstSubtask);

        taskManager.getTaskById(firstTask.getId());
        taskManager.getTaskById(secondTask.getId());

        Task task = taskManager.getTaskById(secondTask.getId());
        System.out.println(task);
        System.out.println();
    }
}