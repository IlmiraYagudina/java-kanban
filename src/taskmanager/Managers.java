package taskmanager;

import history.HistoryManager;
import history.InMemoryHistoryManager;

public class Managers {
    public static TaskManager getInMemoryTaskManager() {
        HistoryManager historyManager = null;
        return new InMemoryTaskManager(historyManager);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


}