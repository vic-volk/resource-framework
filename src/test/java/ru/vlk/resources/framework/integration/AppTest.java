package ru.vlk.resources.framework.integration;

import org.junit.Test;
import ru.vlk.resources.framework.app.App;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;

public class AppTest {

    @Test
    public void testAppRunning() throws IOException {
        App app = new App();
        String result = "";

        String[] args = {"search", "test"};
        result = app.executeCommand(args);

        assertNotEquals(result, "");
    }
}
