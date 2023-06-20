package taskmanager;

import org.junit.jupiter.api.BeforeEach;


public abstract class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryTaskManager();
    }
}