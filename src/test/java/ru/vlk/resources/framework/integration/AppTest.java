package ru.vlk.resources.framework.integration;

import org.junit.Test;
import ru.vlk.resources.framework.app.App;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;

public class AppTest {

    @Test
    public void testSearch() throws IOException {
        App app = new App();
        String result = "";

        String[] args = {"search", "description-test"};
        result = app.executeCommand(args);

        assertNotEquals(result, "");
    }

    @Test
    public void testAdd() throws IOException {
        App app = new App();
        String result = "";

        String[] args = {"add", "url-test", "description-test", "test"};
        result = app.executeCommand(args);

        assertNotEquals(result, "");
    }
}
