package managerTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import enums.TaskStatus;
import taskmanager.FileBackedTasksManager;
import taskmanager.InMemoryTaskManager;
import tasks.Epic;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    public static final Path path = Path.of("data.test.csv");
    File file = new File(String.valueOf(path));
    @BeforeEach
    public void beforeEach() {
        manager = new FileBackedTasksManager(new File("data.csv"));
    }

    @AfterEach
    public void afterEach() {
        try {
            Files.delete(path);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void shouldCorrectlySaveAndLoad() {
        Task task = new Task("Description", "Title", TaskStatus.NEW, Instant.now(), 1);
        manager.createTask(task);
        Epic epic = new Epic("Description", "Title", TaskStatus.NEW, Instant.now(), 0);
        manager.createEpic(epic);
        FileBackedTasksManager fileManager = new FileBackedTasksManager(file);
        fileManager.loadFromFile(file);
        assertEquals(List.of(task), manager.getAllTasks());
        assertEquals(List.of(epic), manager.getAllEpics());
    }

    @Test
    public void shouldSaveAndLoadEmptyTasksEpicsSubtasks() {
        Epic epic = new Epic("Description", "Title", TaskStatus.NEW, Instant.now(), 0);
        manager.createEpic(epic);
        FileBackedTasksManager fileManager = new FileBackedTasksManager(file);
        fileManager.saveToHistory(epic);
        fileManager.loadFromFile(file);
        assertEquals(Collections.EMPTY_LIST, manager.getAllTasks());
        assertEquals(Collections.EMPTY_LIST, manager.getAllEpics());
        assertEquals(Collections.EMPTY_LIST, manager.getAllSubtasks());
    }

    @Test
    public void shouldSaveAndLoadEmptyHistory() {
        Task task = new Task("Description", "Title", TaskStatus.NEW, Instant.now(), 1);
        manager.createTask(task);
        FileBackedTasksManager fileManager = new FileBackedTasksManager(file);
        fileManager.saveToHistory(task);
        fileManager.loadFromFile(file);
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }
}