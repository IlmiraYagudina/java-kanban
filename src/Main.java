package taskmanager;

import enums.TaskStatus;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.nio.file.Path;

import static taskmanager.Managers.getDefaultHistory;

public class Main {

    public static void main(String[] args) throws ManagerSaveException {
        TaskManager taskManager = Managers.getInMemoryTaskManager();

        Task firstTask = new Task("Сделать дизайн-проект", "Ремонт квартиры", TaskStatus.NEW);
        taskManager.createTask(firstTask);
        Task secondTask = new Task("Познакомиться", "Сообщество IT", TaskStatus.NEW);
        taskManager.createTask(secondTask);

        Epic firstEpic = new Epic("Пригласить друзей", "ДР", TaskStatus.NEW);
        taskManager.createEpic(firstEpic);

        Subtask firstSubtask = new Subtask("Сделать проект", "Работа", TaskStatus.NEW, firstEpic.getId());
        taskManager.createSubtask(firstSubtask);

        taskManager.getTaskById(firstTask.getId());
        taskManager.getTaskById(secondTask.getId());
        System.out.println();
    }
}