import taskmanager.ManagerSaveException;
import adapters.InstantAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import history.HistoryManager;
import taskmanager.Managers;
import taskmanager.TaskManager;
import http.KVServer;
import enums.TaskStatus;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import java.time.Instant;

public class Main {

    public static void main(String[] args) throws ManagerSaveException {
        // Спринт 8
        KVServer server;
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();

            server = new KVServer();
            server.start();
            TaskManager httpTaskManager = Managers.getInMemoryTaskManager();

            Task task1 = new Task(
                    "Проект 1", "Интерьер",
                    TaskStatus.NEW,
                    Instant.now(),
                    1
            );
            httpTaskManager.createTask(task1);

            Epic epic1 = new Epic(
                    "Посадить дерево",
                    "Садоводство",
                    TaskStatus.NEW,
                    Instant.now(),
                    2
            );
            httpTaskManager.createEpic(epic1);

            Subtask subtask1 = new Subtask(
                    "Уборка",
                    "Домашние дела",
                    TaskStatus.NEW,
                    epic1.getId(),
                    Instant.now(),
                    3
            );
            httpTaskManager.createSubtask(subtask1);


            httpTaskManager.getTaskById(task1.getId());
            httpTaskManager.getEpicById(epic1.getId());
            httpTaskManager.getSubtaskById(subtask1.getId());

            System.out.println("Печать всех задач");
            System.out.println(gson.toJson(httpTaskManager.getAllTasks()));
            System.out.println("Печать всех эпиков");
            System.out.println(gson.toJson(httpTaskManager.getAllEpics()));
            System.out.println("Печать всех подзадач");
            System.out.println(gson.toJson(httpTaskManager.getAllSubtasks()));
            System.out.println("Загруженный менеджер");
            System.out.println(httpTaskManager);
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//import taskmanager.Managers;
//import taskmanager.TaskManager;
//import tasks.Epic;
//import tasks.Task;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.TreeSet;
//
//import static enums.TaskStatus.NEW;

//
//public class Main {
//    public static void main(String[] args) {
////        TaskManager taskManager =  Managers.getInMemoryTaskManager();
////
////        LocalDateTime dateTime = LocalDateTime.of(2022, 12, 20, 20, 10, 10);
////        Task task1 = new Task("task 1", "taskDescription", NEW,
////                dateTime, Duration.ofMinutes(40));
////        Task task2 = new Task("task 2", "taskDescription",
////                NEW, dateTime.plusMinutes(40), Duration.ofMinutes(10));
////        Task task3 = new Epic("task 3", "taskDescription",
////                NEW);
////        Task task4 = new Task("task 4", "taskDescription",
////                NEW, dateTime.minusMinutes(60), Duration.ofMinutes(10));
////        Task task5 = new Task("task 5", "taskDescription", NEW, dateTime, Duration.ofMinutes(40));
////        Task task6 = new Task("task 6", "taskDescription", NEW, dateTime, Duration.ofMinutes(40));
////
////        taskManager.createTask(task1);
////        taskManager.createTask(task2);
////        taskManager.createTask(task3);
////        taskManager.createTask(task4);
////        taskManager.createTask(task5);
////        taskManager.createTask(task6);
////
////        TreeSet<Task> check = new TreeSet<>((o1, o2) -> {
////            if (o2.getStartTime() == null) {
////                return 1;
////            } else if (o1.getStartTime() == null) {
////                return 1;
////            } else if (o1.getStartTime() == null && o2.getStartTime() == null) {
////                return 0;
////            }
////
////            Duration between = Duration.between(o1.getStartTime(), o2.getStartTime());
////
////            if (between.isNegative()) {
////                return 1;
////            } else if (between.isZero()) {
////                return 0;
////            } else {
////                return -1;
////            }
////        });
////
////        check.addAll(List.of(task6));
////        TreeSet<Task> actual = taskManager.getPrioritizedTasks();
////
////        boolean checker = check.contains(task6);
////
////        System.out.println(checker);
//    }
//}