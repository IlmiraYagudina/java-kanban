package taskmanager;

import http.HTTPTaskManager;
import http.KVServer;
import history.HistoryManager;
import history.InMemoryHistoryManager;

import java.io.IOException;

public class Managers {
    public static InMemoryTaskManager getInMemoryTaskManager() {
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static HTTPTaskManager getDefault(HistoryManager historyManager) throws IOException, InterruptedException {
        return new HTTPTaskManager(historyManager, "http://localhost:" + KVServer.PORT);
    }
}