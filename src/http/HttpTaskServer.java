package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import taskmanager.Managers;
import taskmanager.FileBackedTasksManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import type.TaskType;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;
import java.util.TreeSet;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    private static final int PORT = 8080;

    private final FileBackedTasksManager taskManager;
    private final HttpServer server;
    private final Gson gson;

    public HttpTaskServer() throws IOException {
        this.taskManager = Managers.getFileManager();
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks/task/", this::handleTask);
        server.createContext("/tasks/subtask/", this::handleSubtask);
        server.createContext("/tasks/epic/", this::handleEpic);
        server.createContext("/tasks/subtask/epic/", this::handleEpicSubtasks);
        server.createContext("/tasks/history/", this::handleHistory);
        server.createContext("/tasks/", this::handlePrioritizedTasks);
    }

    public static void main(String[] args) throws IOException {
        File file = Path.of("data.csv").toFile();
        FileBackedTasksManager tasksManager;
        tasksManager = new FileBackedTasksManager(file);
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();

    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("http://localhost:" + PORT + "/tasks");
        server.start();
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту: " + PORT);
    }

    private Optional<Integer> parsePathId(String id) {
        try {
            return Optional.of(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private void deleteHandler(HttpExchange exchange, URI uri, String path, TaskType taskType) throws IOException {

        if (uri.toString().matches("^" + path + "$")) {
            switch (taskType) {
                case TASK:
                    taskManager.deleteAllTasks();
                    break;
                case SUBTASK:
                    taskManager.deleteAllSubtasks();
                    break;
                case EPIC:
                    taskManager.deleteAllEpics();
                    break;
            }

            exchange.sendResponseHeaders(200, 0);

        } else if (uri.toString().matches("^" + path + "\\?id=\\d+$")) {
            String stringId = uri.toString().replaceFirst(path + "\\?id=", "");
            Optional<Integer> id = parsePathId(stringId);

            if (id.isPresent()) {
                switch (taskType) {
                    case TASK:
                        taskManager.deleteTaskById(id.get());
                        break;
                    case SUBTASK:
                        taskManager.deleteSubtaskById(id.get());
                        break;
                    case EPIC:
                        taskManager.deleteEpicById(id.get());
                        break;
                }

                exchange.sendResponseHeaders(200, 0);

            } else {
                System.out.println("NumberFormatException. Please check the address for correctness.");
                exchange.sendResponseHeaders(400, 0);
            }
        } else {
            exchange.sendResponseHeaders(400, 0);
        }
    }

    private void getHandler(HttpExchange exchange, URI uri, String path, TaskType taskType) throws IOException {

        if (uri.toString().matches("^" + path + "$")) {
            String response = null;
            switch (taskType) {
                case TASK:
                    response = gson.toJson(taskManager.getAllTasks());
                    break;
                case SUBTASK:
                    response = gson.toJson(taskManager.getAllSubtasks());
                    break;
                case EPIC:
                    response = gson.toJson(taskManager.getAllEpics());
                    break;
            }

            sendText(exchange, response);

        } else if (uri.toString().matches("^" + path + "\\?id=\\d+$")) {
            String stringId = uri.toString().replaceFirst(path + "\\?id=", "");
            Optional<Integer> id = parsePathId(stringId);
            String response = null;

            if (id.isPresent()) {
                switch (taskType) {
                    case TASK:
                        response = gson.toJson(taskManager.getTaskById(id.get()));
                        break;
                    case SUBTASK:
                        response = gson.toJson(taskManager.getSubtaskById(id.get()));
                        break;
                    case EPIC:
                        response = gson.toJson(taskManager.getEpicById(id.get()));
                        break;
                }

                sendText(exchange, response);

            } else {
                System.out.println("NumberFormatException. Please check the address for correctness.");
                exchange.sendResponseHeaders(400, 0);
            }
        } else {
            exchange.sendResponseHeaders(400, 0);
        }
    }

    private void postHandler(HttpExchange exchange, URI uri, String path, TaskType taskType) throws IOException {

        if (uri.toString().matches("^" + path + "$")) {

            String body = readText(exchange);

            if (body.isEmpty()) {
                System.out.println("Value для сохранения пустой. value указывается в теле запроса");
                exchange.sendResponseHeaders(400, 0);
                return;
            }

            switch (taskType) {
                case TASK:
                    Task task = gson.fromJson(readText(exchange), Task.class);
                    taskManager.createTask(task);
                    break;
                case SUBTASK:
                    Subtask subtask = gson.fromJson(readText(exchange), Subtask.class);
                    taskManager.createSubtask(subtask);
                    break;
                case EPIC:
                    Epic epic = gson.fromJson(readText(exchange), Epic.class);
                    taskManager.createEpic(epic);
                    break;
            }

            exchange.sendResponseHeaders(202, 0);

        } else {
            exchange.sendResponseHeaders(400, 0);
        }
    }

    private void getEndpoint(HttpExchange exchange, String typeTaskPath, TaskType taskType) throws IOException {
        URI uri = exchange.getRequestURI();
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                getHandler(exchange, uri, typeTaskPath, taskType);
                break;
            case "POST":
                postHandler(exchange, uri, typeTaskPath, taskType);
                break;
            case "DELETE":
                deleteHandler(exchange, uri, typeTaskPath, taskType);
                break;

            default:
                System.out.println("Method not allowed. Expected: GET, POST, DELETE. Received: " + method);
                exchange.sendResponseHeaders(405, 0);
        }
    }

    private void handleTask(HttpExchange exchange) {
        try {
            String taskPath = "/tasks/task/";
            getEndpoint(exchange, taskPath, TaskType.TASK);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    private void handleSubtask(HttpExchange exchange) {
        try {
            String subtaskPath = "/tasks/subtask/";
            getEndpoint(exchange, subtaskPath, TaskType.SUBTASK);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    private void handleEpic(HttpExchange exchange) {
        try {
            String epicPath = "/tasks/epic/";
            getEndpoint(exchange, epicPath, TaskType.EPIC);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    private void handlePrioritizedTasks(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                if (path.matches("^/tasks$")) {
                    //TODO
                    String response = gson.toJson(taskManager.getPrioritizedTasks(), TreeSet.class);
                    sendText(exchange, response);

                } else {
                    exchange.sendResponseHeaders(400, 0);
                }
            } else {
                exchange.sendResponseHeaders(405, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    private void handleEpicSubtasks(HttpExchange exchange) {
        URI uri = exchange.getRequestURI();
        String method = exchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                if (uri.toString().matches("^/tasks/subtask/epic/\\?id=\\d+$")) {
                    String stringId = uri.toString().replaceFirst("/tasks/subtask/epic/\\?id=", "");
                    Optional<Integer> id = parsePathId(stringId);

                    if (id.isPresent()) {
                        Epic epic = taskManager.getEpicById(id.get());
                        String response = gson.toJson(taskManager.getEpicSubtasks(epic));

                        sendText(exchange, response);

                    } else {
                        System.out.println("NumberFormatException. Please check the address for correctness.");
                        exchange.sendResponseHeaders(400, 0);
                    }
                } else {
                    exchange.sendResponseHeaders(400, 0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    private void handleHistory(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                if (path.matches("^/tasks/history$")) {
                    String response = gson.toJson(taskManager.getHistory());
                    sendText(exchange, response);

                } else {
                    exchange.sendResponseHeaders(400, 0);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }
}