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


//        Спринт 6
//        TaskManager taskManager = Managers.getInMemoryTaskManager();
//
//        System.out.println("*** Test History ***");
//        System.out.println("--- Create ---");
//        taskManager.createTask(new Task("Описание-1", "Task-1", TaskStatus.IN_PROGRESS));
//        taskManager.createTask(new Task("Описание-2", "Task-2", TaskStatus.NEW));
//        taskManager.createEpic(new Epic("Описание-1", "Epic-1", TaskStatus.DONE));
//        taskManager.createEpic(new Epic("Описание-2", "Epic-2", TaskStatus.NEW));
//        taskManager.createSubtask(new Subtask("Описание-1", "Subtask-1", TaskStatus.NEW, 3));
//        taskManager.createSubtask(new Subtask("Описание-2", "Subtask-2", TaskStatus.NEW, 3));
//        taskManager.createSubtask(new Subtask("Описание-3", "Subtask-3", TaskStatus.NEW, 4));
//        taskManager.createSubtask(new Subtask("Описание-4", "Subtask-4", TaskStatus.IN_PROGRESS, 4));
//
//        System.out.println("--- Get By Id ---");
//        taskManager.getTaskById(1);
//        taskManager.getEpicById(3);
//        taskManager.getTaskById(2);
//        taskManager.getSubtaskById(5);
//        taskManager.getSubtaskById(6);
//        taskManager.getEpicById(4);
//        taskManager.getSubtaskById(5);
//        taskManager.getSubtaskById(6);
//        taskManager.getSubtaskById(7);
//        taskManager.getSubtaskById(8);
//        taskManager.getSubtaskById(7);
//        taskManager.getTaskById(2);
//
//        System.out.println("--- Get History ---");
//        List<Task> history = taskManager.getHistory();
//        System.out.println(history);
//
//        System.out.println("--- Remove from history ---");
//        taskManager.remove(1);
//        taskManager.deleteEpicById(3);
//
//        List<Task> historyAfterRemove = taskManager.getHistory();
//        System.out.println(historyAfterRemove);