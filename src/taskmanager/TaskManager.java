package taskmanager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getHistory();

    int createTask(Task task);

    int createEpic(Epic epic);

    int createSubtask(Subtask subtask);

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubtaskById(int id);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    List<Subtask> getAllSubtasksByEpicId(int id);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateStatusEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void printTasks();

    void printEpics();

    void printSubtasks();
}

//import enums.TaskStatus;
//import tasks.Epic;
//import tasks.Subtask;
//import tasks.Task;
//
//import java.util.*;
//
//public class TaskManager {
//    private static int id = 0;
//
//    private Map<Integer, Task> tasks = new HashMap<>();
//    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
//    private HashMap<Integer, Epic> epics = new HashMap<>();
//
//    public int generateId() {
//        return ++id;
//    }
//
//    public int createTask(Task task) {
//        int newTaskId = generateId();
//        task.setId(newTaskId);
//        tasks.put(newTaskId, task);
//        return newTaskId;
//    }
//
//    public int createEpic(Epic epic) {
//        int newEpicId = generateId();
//        epic.setId(newEpicId);
//        epics.put(newEpicId, epic);
//        return newEpicId;
//    }
//
//    public int createSubtask(Subtask subtask) {
//        int newSubtaskId = generateId();
//        subtask.setId(newSubtaskId);
//        Epic epic = epics.get(subtask.getEpicId());
//        if (epic != null) {
//            subtasks.put(newSubtaskId, subtask);
//            epic.setSubtaskIds(newSubtaskId);
//            updateStatusEpic(epic);
//            return newSubtaskId;
//        } else {
//            System.out.println("Epic not found");
//            return -1;
//        }
//    }
//
//    public void deleteTaskById(int id) {
//        tasks.remove(id);
//    }
//
//    public void deleteEpicById(int id) {
//        Epic epic = epics.get(id);
//        if (epic != null) {
//            for (Integer subtaskId : epic.getSubtaskIds()) {
//                subtasks.remove(subtaskId);
//            }
//            epics.remove(id);
//        } else {
//            System.out.println("Epic not found");
//        }
//    }
//
//    public void deleteSubtaskById(int id) {
//        Subtask subtask = subtasks.get(id);
//        if (subtask != null) {
//            Epic epic = epics.get(subtask.getEpicId());
//            epic.getSubtaskIds().remove((Integer) subtask.getId());
//            updateStatusEpic(epic);
//            subtasks.remove(id);
//        } else {
//            System.out.println("Subtask not found");
//        }
//    }
//
//    public void deleteAllTasks() {
//        tasks.clear();
//    }
//
//    public void deleteAllEpics() {
//        subtasks.clear();
//        epics.clear();
//    }
//
//    public void deleteAllSubtasks() {
//        subtasks.clear();
//        for (Epic epic : epics.values()) {
//            epic.getSubtaskIds().clear();
//            updateStatusEpic(epic);
//        }
//    }
//
//    public Task getTaskById(int id) {
//        return tasks.get(id);
//    }
//
//    public Epic getEpicById(int id) {
//        return epics.get(id);
//    }
//
//    public Subtask getSubtaskById(int id) {
//        return subtasks.get(id);
//    }
//
//    public List<Task> getAllTasks() {
//        return new ArrayList<>(tasks.values());
//    }
//
//    public List<Epic> getAllEpics() {
//        return new ArrayList<>(epics.values());
//    }
//
//    public List<Subtask> getAllSubtasks() {
//        return new ArrayList<>(subtasks.values());
//    }
//
//    public List<Subtask> getAllSubtasksByEpicId(int id) {
//        if (epics.containsKey(id)) {
//            List<Subtask> subtasksNew = new ArrayList<>();
//            Epic epic = epics.get(id);
//            for (int i = 0; i < epic.getSubtaskIds().size(); i++) {
//                subtasksNew.add(subtasks.get(epic.getSubtaskIds().get(i)));
//            }
//            return subtasksNew;
//        } else {
//            return Collections.emptyList();
//        }
//    }
//
//    public void updateTask(Task task) {
//        if (tasks.containsKey(task.getId())) {
//            tasks.put(task.getId(), task);
//        } else {
//            System.out.println("Task not found");
//        }
//    }
//
//    public void updateEpic(Epic epic) {
//        if (epics.containsKey(epic.getId())) {
//            epics.put(epic.getId(), epic);
//            updateStatusEpic(epic);
//        } else {
//            System.out.println("Epic not found");
//        }
//    }
//
//    public void updateStatusEpic(Epic epic) {
//        if (epics.containsKey(epic.getId())) {
//            if (epic.getSubtaskIds().size() == 0) {
//                epic.setStatus(TaskStatus.NEW);
//            } else {
//                List<Subtask> subtasksNew = new ArrayList<>();
//                int countDone = 0;
//                int countNew = 0;
//
//                for (int i = 0; i < epic.getSubtaskIds().size(); i++) {
//                    subtasksNew.add(subtasks.get(epic.getSubtaskIds().get(i)));
//                }
//
//                for (Subtask subtask : subtasksNew) {
//                    if (subtask.getStatus() == TaskStatus.DONE) {
//                        countDone++;
//                    }
//                    if (subtask.getStatus() == TaskStatus.NEW) {
//                        countNew++;
//                    }
//                    if (subtask.getStatus() == TaskStatus.IN_PROGRESS) {
//                        epic.setStatus(TaskStatus.IN_PROGRESS);
//                        return;
//                    }
//                }
//
//                if (countDone == epic.getSubtaskIds().size()) {
//                    epic.setStatus(TaskStatus.DONE);
//                } else if (countNew == epic.getSubtaskIds().size()) {
//                    epic.setStatus(TaskStatus.NEW);
//                } else {
//                    epic.setStatus(TaskStatus.IN_PROGRESS);
//                }
//            }
//        } else {
//            System.out.println("Epic not found");
//        }
//    }
//
//    public void updateSubtask(Subtask subtask) {
//        if (subtasks.containsKey(subtask.getId())) {
//            subtasks.put(subtask.getId(), subtask);
//            Epic epic = epics.get(subtask.getEpicId());
//            updateStatusEpic(epic);
//        } else {
//            System.out.println("Subtask not found");
//        }
//    }
//
//    public void printTasks() {
//        if (tasks.size() == 0) {
//            System.out.println("Task list is empty");
//            return;
//        }
//        for (Task task : tasks.values()) {
//            System.out.println("Task{" +
//                    "description='" + task.getDescription() + '\'' +
//                    ", id=" + task.getId() +
//                    ", name='" + task.getName() + '\'' +
//                    ", status=" + task.getStatus() +
//                    '}');
//        }
//    }
//
//    public void printEpics() {
//        if (epics.size() == 0) {
//            System.out.println("Epic list is empty");
//            return;
//        }
//        for (Epic epic : epics.values()) {
//            System.out.println("Epic{" +
//                    "subtasksIds=" + epic.getSubtaskIds() +
//                    ", description='" + epic.getDescription() + '\'' +
//                    ", id=" + epic.getId() +
//                    ", name='" + epic.getName() + '\'' +
//                    ", status=" + epic.getStatus() +
//                    '}');
//        }
//    }
//
//    public void printSubtasks() {
//        if (subtasks.size() == 0) {
//            System.out.println("Subtask list is empty");
//            return;
//        }
//        for (Subtask subtask : subtasks.values()) {
//            System.out.println("Subtask{" +
//                    "epicId=" + subtask.getEpicId() +
//                    ", description='" + subtask.getDescription() + '\'' +
//                    ", id=" + subtask.getId() +
//                    ", name='" + subtask.getName() + '\'' +
//                    ", status=" + subtask.getStatus() +
//                    '}');
//        }
//    }
//}