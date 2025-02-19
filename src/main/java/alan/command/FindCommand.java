package alan.command;

import alan.core.TaskList;
import alan.core.Task;
import alan.core.Deadline;
import alan.core.Event;
import alan.exception.AlanException;
import alan.storage.Storage;
import alan.ui.Ui;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class FindCommand extends Command {
    private final String searchTerm;
    private final boolean isDateSearch;
    private LocalDate targetDate;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FindCommand(String searchTerm, boolean isDateSearch) throws AlanException {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new AlanException("The search term cannot be empty.");
        }

        this.searchTerm = searchTerm.trim();
        this.isDateSearch = isDateSearch;

        if (isDateSearch) {
            try {
                this.targetDate = LocalDate.parse(searchTerm, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                throw new AlanException("Date must be in the format: YYYY-MM-DD");
            }
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (isDateSearch) {
            findByDate(tasks, ui);
        } else {
            findByKeyword(tasks, ui);
        }
    }

    private void findByDate(TaskList tasks, Ui ui) {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks.getTasks()) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.getBy().toLocalDate().equals(targetDate)) {
                    matchingTasks.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                LocalDate startDate = event.getStartTime().toLocalDate();
                LocalDate endDate = event.getEndTime().toLocalDate();

                if ((startDate.equals(targetDate) || endDate.equals(targetDate)) ||
                        (startDate.isBefore(targetDate) && endDate.isAfter(targetDate))) {
                    matchingTasks.add(task);
                }
            }
        }

        ui.showTasksForDate(matchingTasks, targetDate);
    }

    private void findByKeyword(TaskList tasks, Ui ui) {
        ArrayList<Task> matchingTasks = tasks.getTasks().stream()
                .filter(task -> task.getDescription().toLowerCase()
                        .contains(searchTerm.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));

        ui.showSearchResults(matchingTasks, searchTerm);
    }
}