package http;

import adapters.InstantAdapter;
import com.google.gson.*;
import history.HistoryManager;
import taskmanager.FileBackedTasksManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {

    final static String KEY_TASKS = "tasks";
    final static String KEY_SUBTASKS = "subtasks";
    final static String KEY_EPICS = "epics";
    final static String KEY_HISTORY = "history";
    final KVTaskClient client;
    private static Gson gson =
            new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();

    public HttpTaskManager(String path) throws IOException, InterruptedException {
        super();
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        client = new KVTaskClient(path);

        JsonElement jsonTasks = JsonParser.parseString(client.load(KEY_TASKS));
        if (!jsonTasks.isJsonNull()) {
            JsonArray jsonTasksArray = jsonTasks.getAsJsonArray();
            for (JsonElement jsonTask : jsonTasksArray) {
                Task task = gson.fromJson(jsonTask, Task.class);
                this.addTask(task);
            }
        }

        JsonElement jsonEpics = JsonParser.parseString(client.load(KEY_EPICS));
        if (!jsonEpics.isJsonNull()) {
            JsonArray jsonEpicsArray = jsonEpics.getAsJsonArray();
            for (JsonElement jsonEpic : jsonEpicsArray) {
                Epic task = gson.fromJson(jsonEpic, Epic.class);
                this.addEpic(task);
            }
        }

        JsonElement jsonSubtasks = JsonParser.parseString(client.load(KEY_SUBTASKS));
        if (!jsonSubtasks.isJsonNull()) {
            JsonArray jsonSubtasksArray = jsonSubtasks.getAsJsonArray();
            for (JsonElement jsonSubtask : jsonSubtasksArray) {
                Subtask task = gson.fromJson(jsonSubtask, Subtask.class);
                this.addSubtask(task);
            }
        }

        JsonElement jsonHistoryList = JsonParser.parseString(client.load(KEY_HISTORY));
        if (!jsonHistoryList.isJsonNull()) {
            JsonArray jsonHistoryArray = jsonHistoryList.getAsJsonArray();
            for (JsonElement jsonTaskId : jsonHistoryArray) {
                int taskId = jsonTaskId.getAsInt();
                if (this.subtasks.containsKey(taskId)) {
                    this.getSubtaskById(taskId);
                } else if (this.epics.containsKey(taskId)) {
                    this.getEpicById(taskId);
                } else if (this.tasks.containsKey(taskId)) {
                    this.getTaskById(taskId);
                }
            }
        }
    }

    @Override
    public void save() {
        client.put(KEY_TASKS, gson.toJson(tasks.values()));
        client.put(KEY_SUBTASKS, gson.toJson(subtasks.values()));
        client.put(KEY_EPICS, gson.toJson(epics.values()));
        client.put(KEY_HISTORY, gson.toJson(this.getHistory()
                .stream()
                .map(Task::getId)
                .collect(Collectors.toList())));
    }

}

//package http;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import taskmanager.FileBackedTasksManager;
//import tasks.Epic;
//import tasks.Subtask;
//import tasks.Task;
//
//import java.io.IOException;
//
//public class HttpTaskManager extends FileBackedTasksManager {
//
//    private final KVTaskClient taskClient;
//    private final Gson gson;
//
//    public HttpTaskManager(String path) throws IOException, InterruptedException {
//        super();
//        gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .create();
//
//        taskClient = new KVTaskClient(path);
//    }
//
//    @Override
//    public Task createTask(Task task) {
//        super.createTask(task);
//
//        taskClient.put(task.getId().toString(), gson.toJson(task));
//
//        return task;
//    }
//
//    @Override
//    public Subtask createSubtask(Subtask subtask) {
//        super.createSubtask(subtask);
//
//        taskClient.put(subtask.getSubtaskIds().toString(), gson.toJson(subtask));
//
//        return subtask;
//    }
//
//    @Override
//    public Epic createEpic(Epic epic) {
//        super.createSubtask(epic);
//
//        taskClient.put(epic.getId().toString(), gson.toJson(epic));
//
//        return epic;
//    }
//
//    @Override
//    public Task getTaskById(int id) {
//        return gson.fromJson(taskClient.load(String.valueOf(id)), Task.class);
//    }
//
//    @Override
//    public Subtask getSubtaskById(int id) {
//        return gson.fromJson(taskClient.load(String.valueOf(id)), Subtask.class);
//    }
//
//    @Override
//    public Epic getEpicById(int id) {
//        return gson.fromJson(taskClient.load(String.valueOf(id)), Epic.class);
//    }
//
//    @Override
//    public void updateTask(Task task) {
//        if (task.getId() == null)
//            return;
//
//        taskClient.put(task.getId().toString(), gson.toJson(task));
//    }
//
//    @Override
//    public void updateSubtask(Subtask subtask) {
//        if (subtask.getId() == null) {
//            return;
//        }
//
//        taskClient.put(subtask.getId().toString(), gson.toJson(subtask));
//    }
//
//    @Override
//    public void updateEpic(Epic epic) {
//        if (epic.getId() == null)
//            return;
//
//        taskClient.put(epic.getId().toString(), gson.toJson(epic));
//    }
//}