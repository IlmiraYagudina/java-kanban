import taskmanager.Managers;
import taskmanager.TaskManager;
import enums.TaskStatus;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getInMemoryTaskManager();

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

        System.out.println("--- Remove from history ---");
        taskManager.remove(1);
        taskManager.deleteEpicById(3);

        List<Task> historyAfterRemove = taskManager.getHistory();
        System.out.println(historyAfterRemove);
    }
}