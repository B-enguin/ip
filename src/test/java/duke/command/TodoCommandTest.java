package duke.command;

import duke.DukeException;
import duke.Storage;
import duke.task.TaskList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TodoCommandTest {

    @Test
    public void todoCorrect(@TempDir Path savePath) throws IOException {
        try {
            Storage storage = new Storage(savePath.resolve("duke.txt").toString());
            TaskList taskList = storage.load();
            Command c = new TodoCommand("task");
            c.execute(taskList, null, storage);
            assertEquals("[T][ ] task",taskList.get(0).toString());

            List<String> lines = List.of("0 todo task");
            assertLinesMatch(lines, Files.readAllLines(savePath.resolve("duke.txt")));
        } catch (DukeException ignored) {
        }
    }

    @Test
    public void missingArgument_throwsException() {
        assertThrows(DukeException.class, () -> {
            new TodoCommand(null).execute(null, null, null);
        });
    }
}
