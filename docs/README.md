# Alan Task Manager - User Guide

Alan is a desktop task management application optimized for users who prefer to work with a Command Line Interface (CLI). With Alan, you can efficiently manage your todos, deadlines, and events through simple text commands.

## Quick Start

1. Ensure you have Java 17 or above installed on your computer
2. Download the latest version of Alan from [here]
3. Copy the file to a folder you want to use as the home folder for Alan
4. Open a command prompt and navigate to the folder
5. Type `java -jar alan.jar` to start the application
6. Type `help` to view available commands

## Features

### Adding Tasks

Alan supports three types of tasks to help you organize your activities effectively:

#### Creating Todo Tasks

Add simple todo items to your task list.

Format: `todo DESCRIPTION`

Example: `todo read chapter 3`

Response:
```
Got it. I've added this task:
  [T][✗] read chapter 3
Now you have 1 task in the list.
```

#### Creating Deadline Tasks

Add tasks with specific deadlines.

Format: `deadline DESCRIPTION /by yyyy-MM-dd HHmm`

Example: `deadline submit report /by 2024-02-19 1430`

Response:
```
Got it. I've added this task:
  [D][✗] submit report (by: Feb 19 2024, 2:30 PM)
Now you have 2 tasks in the list.
```

#### Creating Event Tasks

Add events with start and end times.

Format: `event DESCRIPTION /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm`

Example: `event team meeting /from 2024-02-19 1000 /to 2024-02-19 1200`

Response:
```
Got it. I've added this task:
  [E][✗] team meeting (from: Feb 19 2024, 10:00 AM to: Feb 19 2024, 12:00 PM)
Now you have 3 tasks in the list.
```

### Managing Tasks

#### Viewing All Tasks

View your complete task list.

Format: `list`

Response:
```
Here are the tasks in your list:
1.[T][✗] read chapter 3
2.[D][✗] submit report (by: Feb 19 2024, 2:30 PM)
3.[E][✗] team meeting (from: Feb 19 2024, 10:00 AM to: Feb 19 2024, 12:00 PM)
```

#### Marking Tasks as Done/Undone

Mark tasks as completed or incomplete.

Format:
- Mark as done: `mark INDEX`
- Mark as not done: `unmark INDEX`

Example: `mark 1`

Response:
```
Nice! I've marked this task as done:
  [T][✓] read chapter 3
```

#### Deleting Tasks

Remove tasks from your list.

Format: `delete INDEX`

Example: `delete 2`

Response:
```
Noted. I've removed this task:
  [D][✗] submit report (by: Feb 19 2024, 2:30 PM)
Now you have 2 tasks in the list.
```

### Finding Tasks

Alan provides two ways to find tasks:

#### Search by Keyword

Find tasks containing specific keywords.

Format: `find KEYWORD`

Example: `find meeting`

Response:
```
Here are the matching tasks in your list:
1.[E][✗] team meeting (from: Feb 19 2024, 10:00 AM to: Feb 19 2024, 12:00 PM)
```

#### Search by Date

Find tasks occurring on a specific date.

Format: `find /date yyyy-MM-dd`

Example: `find /date 2024-02-19`

Response:
```
Here are the tasks for Feb 19 2024:
1.[E][✗] team meeting (from: Feb 19 2024, 10:00 AM to: Feb 19 2024, 12:00 PM)
```

### Exiting the Program

Close the application.

Format: `bye`

Response:
```
Bye. Hope to see you again soon!
```

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| Add Todo | `todo DESCRIPTION` | `todo read chapter 3` |
| Add Deadline | `deadline DESCRIPTION /by yyyy-MM-dd HHmm` | `deadline submit report /by 2024-02-19 1430` |
| Add Event | `event DESCRIPTION /from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm` | `event team meeting /from 2024-02-19 1000 /to 2024-02-19 1200` |
| List Tasks | `list` | `list` |
| Mark Done | `mark INDEX` | `mark 1` |
| Mark Not Done | `unmark INDEX` | `unmark 1` |
| Delete | `delete INDEX` | `delete 1` |
| Find by Keyword | `find KEYWORD` | `find meeting` |
| Find by Date | `find /date yyyy-MM-dd` | `find /date 2024-02-19` |
| Exit | `bye` | `bye` |

## Data Storage

Alan automatically saves all your tasks to a file in the `data` folder. This ensures your tasks persist between sessions. The data file is automatically created when you first run the application.