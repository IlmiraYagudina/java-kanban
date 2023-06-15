package taskmanager;

import history.HistoryManager;
import history.InMemoryHistoryManager;

public class Managers {

    public static InMemoryTaskManager getInMemoryTaskManager() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager() {

        };
    }


}