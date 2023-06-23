package managerTest;

import org.junit.jupiter.api.BeforeEach;
import taskmanager.InMemoryTaskManager;


public abstract class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryTaskManager();
    }
}