package taskmanager;

import type.TaskType;
import enums.TaskStatus;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import history.HistoryManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FileBackedTasksManager extends InMemoryTaskManager {
    private static File file;
    private static final String HEADER_CSV_FILE = "id,type,name,status,description,epic\n";


    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public static void loadFromFile(File file) {
        String[] content;
        Map<Long, Task> taskMap = new HashMap<>();

        try {
            content = Files.readString(file.toPath(), StandardCharsets.UTF_8).split("\n");
        } catch (IOException io) {

            throw new ManagerSaveException(io.getMessage());
        }

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        for (int i = 1; i < content.length; i++) {
            if (content[i].isEmpty()) {
                int j = ++i;
                List<Integer> historyFromString = historyFromString(content[j]);
                for (Integer id : historyFromString) fileBackedTasksManager.saveToHistory(taskMap.get(id));
                return;
            }

            Task task = fromString(content[i]);
            taskMap.put((long) task.getId(), task);
            if (task instanceof Epic) {
                fileBackedTasksManager.addEpic((Epic) task);
            } else if (task instanceof Subtask) {
                fileBackedTasksManager.addSubtask((Subtask) task);
            } else {
                fileBackedTasksManager.addTask(task);
            }
        }
    }

    protected void saveToHistory(Task task) {
        historyManager.add(task);
    }

    public void save() {
        try {
            if (Files.exists(file.toPath())) {
                Files.delete(file.toPath());
            }
            Files.createFile(file.toPath());
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось найти файл для записи данных");
        }

        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(HEADER_CSV_FILE);

            for (Task task : getAllTasks()) {
                writer.write(toString(task) + "\n");
            }

            for (Epic epic : getAllEpics()) {
                writer.write(toString(epic) + "\n");
            }

            for (Subtask subtask : getAllSubtasks()) {
                writer.write(toString(subtask) + "\n");
            }

            writer.write("\n");
            writer.write(historyToString(getHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить в файл", e);

        }
    }


    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        saveToHistory(task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        saveToHistory(epic);
        return epic;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        saveToHistory(subtask);
        return subtask;
    }

    public void addTask(Task task) {
        super.createTask(task);
    }

    public void addEpic(Epic epic) {
        super.createEpic(epic);
    }

    public void addSubtask(Subtask subtask) {
        super.createSubtask(subtask);
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public Task getTaskById(int id) {
        try {
            Task task = super.getTaskById(id);
            saveToHistory(task);
            return task;
        } catch (NullPointerException exp) { // ловим исключение NullPointerException
            System.out.println("Ошибка: передан неинициализированный объект!");
        }
        return null;
    }

    @Override
    public Epic getEpicById(int id) {
        try {
            Epic epic = super.getEpicById(id);
            saveToHistory(epic);
            return epic;
        } catch (NullPointerException exp) { // ловим исключение NullPointerException
            System.out.println("Ошибка: передан неинициализированный объект!");
        }
        return null;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        try {
            Subtask subtask = super.getSubtaskById(id);
            saveToHistory(subtask);
            return subtask;
        } catch (NullPointerException exp) { // ловим исключение NullPointerException
            System.out.println("Ошибка: передан неинициализированный объект!");
        }
        return null;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        saveToHistory(task);
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        saveToHistory(epic);
    }


    // Метод для сохранения истории в CSV
    static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        StringBuilder str = new StringBuilder();

        if (history.isEmpty()) {
            return "";
        }

        for (Task task : history) {
            str.append(task.getId()).append(",");
        }

        if (str.length() != 0) {
            str.deleteCharAt(str.length() - 1);
        }

        return str.toString();
    }

    // Метод восстановления менеджера истории из CSV
    static List<Integer> historyFromString(String value) {
        List<Integer> toReturn = new ArrayList<>();
        if (value != null) {
            String[] id = value.split(",");

            for (String number : id) {
                toReturn.add(Integer.parseInt(number));
            }

            return toReturn;
        }
        return toReturn;
    }

    private String getParentEpicId(Task task) {
        if (task instanceof Subtask) {
            return Integer.toString(((Subtask) task).getEpicId());
        }
        return "";
    }

    private TaskType getType(Task task) {
        if (task instanceof Epic) {
            return TaskType.EPIC;
        } else if (task instanceof Subtask) {
            return TaskType.SUBTASK;
        }
        return TaskType.TASK;
    }

    // Метод сохранения задачи в строку
    private String toString(Task task) {
        String[] toJoin = {Integer.toString(task.getId()), getType(task).toString(), task.getName(),
                task.getStatus().toString(), task.getDescription(), getParentEpicId(task)};
        return String.join(",", toJoin);
    }

    // Метод создания задачи из строки
    private static Task fromString(String value) {
        String[] params = value.split(",");
        int id = Integer.parseInt(params[0]);
        String type = params[1];
        String name = params[2];
        TaskStatus status = TaskStatus.valueOf(params[3].toUpperCase());
        String description = params[4];
        Instant startTime = Instant.parse(params[5]);
        long duration = Long.parseLong(params[6]);
        Integer epicId = type.equals("SUBTASK") ? Integer.parseInt(params[7]) : null;

        if (type.equals("EPIC")) {
            Epic epic = new Epic(description, name, status, startTime, duration);
            epic.setId(id);
            epic.setStatus(status);
            return epic;
        } else if (type.equals("SUBTASK")) {
            Subtask subtask = new Subtask(description, name, status, epicId, startTime, duration);
            subtask.setId(id);
            return subtask;
        } else {
            Task task = new Task(description, name, status, startTime, duration);
            task.setId(id);
            return task;
        }
    }
}