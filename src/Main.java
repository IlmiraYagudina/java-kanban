
import taskmanager.Managers;
import taskmanager.TaskManager;
import enums.TaskStatus;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getInMemoryTaskManager(Managers.getDefaultHistory());

        System.out.println("*** Test History ***");
        System.out.println("--- Create ---");
        taskManager.createTask(new Task("Описание-1", "Task-1", TaskStatus.IN_PROGRESS));
        taskManager.createTask(new Task("Описание-2", "Task-2", TaskStatus.NEW));
        taskManager.createEpic(new Epic("Описание-1", "Epic-1", TaskStatus.DONE));
        taskManager.createEpic(new Epic("Описание-2", "Epic-2", TaskStatus.NEW));
        taskManager.createSubtask(new Subtask("Описание-1", "Subtask-1", TaskStatus.NEW, 3));
        taskManager.createSubtask(new Subtask("Описание-2", "Subtask-2", TaskStatus.NEW, 3));
        taskManager.createSubtask(new Subtask("Описание-3", "Subtask-3", TaskStatus.NEW, 4));
        taskManager.createSubtask(new Subtask("Описание-4", "Subtask-4", TaskStatus.IN_PROGRESS, 4));

        System.out.println("--- Get By Id ---");
        taskManager.getTaskById(1);
        taskManager.getEpicById(3);
        taskManager.getTaskById(2);
        taskManager.getSubtaskById(5);
        taskManager.getSubtaskById(6);
        taskManager.getEpicById(4);
        taskManager.getSubtaskById(5);
        taskManager.getSubtaskById(6);
        taskManager.getSubtaskById(7);
        taskManager.getSubtaskById(8);
        taskManager.getSubtaskById(7);
        taskManager.getTaskById(2);

        System.out.println("--- Get History ---");
        List<Task> history = taskManager.getHistory();
        System.out.println(history);
    }
}

//import enums.TaskStatus;
//import taskmanager.TaskManager;
//import tasks.Epic;
//import tasks.Subtask;
//import tasks.Task;
//
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        TaskManager taskManager = new TaskManager();
//
//        System.out.println("*** Task ***");
//        System.out.println("--- Create task ---");
//        taskManager.createTask(new Task("Описание-1", "Task-1", TaskStatus.NEW));
//        taskManager.createTask(new Task("Описание-2", "Task-2", TaskStatus.NEW));
//        taskManager.printTasks();
//        System.out.println("--- Get all tasks ---");
//        List<Task> taskList = taskManager.getAllTasks();
//        System.out.println(taskList);
//        System.out.println("--- Get task by id ---");
//        Task task = taskManager.getTaskById(1);
//        System.out.println(task);
//        System.out.println("--- Update task ---");
//        task.setStatus(TaskStatus.IN_PROGRESS);
//        taskManager.updateTask(task);
//        System.out.println(task);
//        System.out.println();
//
//        System.out.println("*** Epic ***");
//        System.out.println("--- Create epic ---");
//        taskManager.createEpic(new Epic("Описание-1", "Epic-1", TaskStatus.NEW));
//        taskManager.createEpic(new Epic("Описание-2", "Epic-2", TaskStatus.NEW));
//        taskManager.printEpics();
//        System.out.println("--- Get all epics ---");
//        List<Epic> epics = taskManager.getAllEpics();
//        System.out.println(epics);
//        System.out.println("--- Get epic by id ---");
//        Epic epic = taskManager.getEpicById(3);
//        System.out.println(epic);
//        System.out.println("--- Update epic ---");
//        epic.setStatus(TaskStatus.IN_PROGRESS);
//        taskManager.updateEpic(epic);
//        Epic epic3 = taskManager.getEpicById(3);
//        System.out.println(epic3);
//        System.out.println();
//
//        System.out.println("*** Subtask ***");
//        System.out.println("--- Create subtask ---");
//        taskManager.createSubtask(new Subtask("Описание-1", "Subtask-1", TaskStatus.NEW, 3));
//        taskManager.createSubtask(new Subtask("Описание-2", "Subtask-2", TaskStatus.NEW, 3));
//        taskManager.createSubtask(new Subtask("Описание-3", "Subtask-3", TaskStatus.NEW, 4));
//        taskManager.createSubtask(new Subtask("Описание-4", "Subtask-4", TaskStatus.NEW, 4));
//        taskManager.printSubtasks();
//        System.out.println("--- Get all subtasks by epic id ---");
//        List<Subtask> subtasksByEpicId = taskManager.getAllSubtasksByEpicId(3);
//        System.out.println(subtasksByEpicId);
//        System.out.println("--- Get all subtasks ---");
//        List<Subtask> subtasks = taskManager.getAllSubtasks();
//        System.out.println(subtasks);
//        System.out.println("--- Get subtask by id ---");
//        Subtask subtask = taskManager.getSubtaskById(5);
//        System.out.println(subtask);
//        System.out.println("--- Update subtask ---");
//        subtask.setStatus(TaskStatus.IN_PROGRESS);
//        taskManager.updateSubtask(subtask);
//        System.out.println(subtask);
//        System.out.println();
//
//        System.out.println("*** Delete ***");
//        System.out.println("--- Delete task by id ---");
//        taskManager.deleteTaskById(1);
//        System.out.println(taskList);
//        System.out.println("--- Delete all tasks ---");
//        taskManager.deleteAllTasks();
//        taskManager.printTasks();
//        System.out.println("--- Delete subtask by id ---");
//        taskManager.deleteSubtaskById(5);
//        taskManager.printSubtasks();
//        System.out.println("--- Delete all subtasks ---");
//        taskManager.deleteAllSubtasks();
//        taskManager.printSubtasks();
//        System.out.println("--- Delete epic by id ---");
//        taskManager.deleteEpicById(4);
//        taskManager.printEpics();
//        System.out.println("--- Delete all epics ---");
//        taskManager.deleteAllEpics();
//        taskManager.printEpics();
//    }
//}
