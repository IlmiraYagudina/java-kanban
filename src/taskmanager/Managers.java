package taskmanager;

import history.HistoryManager;
import history.InMemoryHistoryManager;

public class Managers {
    public static InMemoryTaskManager getInMemoryTaskManager(HistoryManager historyManager) {
        return new InMemoryTaskManager(historyManager);
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}