package com.own.kanbanboard.cmd;

import com.own.kanbanboard.exceptions.ModelException;
import com.own.kanbanboard.exceptions.NoSuchTaskException;
import com.own.kanbanboard.exceptions.NoSuchUserException;
import com.own.kanbanboard.model.Person;
import com.own.kanbanboard.model.Task;
import com.own.kanbanboard.services.ConsoleService;
import com.own.kanbanboard.services.KanbanManager;
import java.util.List;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class KanbanManagerCommands {

  private final KanbanManager kanbanManager;
  private final ConsoleService console;

  public KanbanManagerCommands(KanbanManager kanbanManager, ConsoleService consoleService) {
    this.kanbanManager = kanbanManager;
    this.console = consoleService;
  }

  @ShellMethod("add user to Person db")
  public Person newUser(String sso, String name) throws ModelException {
    Person newUser;
    try {
      newUser = kanbanManager.addPerson(sso, name);
      console.write("add new user: %s. %s", name, sso);
    } catch (ModelException e) {
      console.writeError(e.getMessage());
      return null;
    }
    return newUser;
  }

  @ShellMethod("add task to Task db")
  public Task newTask(String id, String name, @ShellOption(defaultValue = "") String description)
      throws ModelException {
    Task newTask;
    try {
      newTask = kanbanManager.addTask(id, name, description);
      console.write("add new task: %s. %s", name, id);
    } catch (ModelException e) {
      console.writeError(e.getMessage());
      return null;
    }
    return newTask;
  }

  @ShellMethod("Listing all task")
  public void listTask() {
    List<Task> taskList = kanbanManager.listAllTask();
    console.write("Task list:");
    taskList.forEach(task -> console.write(task.getId() + "; " + task.getTaskName()));
  }

  @ShellMethod("Listing all user")
  public void listUser() {
    console.write("Task list:");
    kanbanManager.listAllPersons()
        .forEach(person -> console.write(person.getId() + "; " + person.getName()));
  }

  @ShellMethod("Listing user's task")
  public void listUsersTask(String id) {
    try {
      kanbanManager.getPerson(id).getTasks().forEach(task -> console
          .write("Taskid: %s and name: %s", task.getId().toString(), task.getTaskName()));
    } catch (NoSuchUserException e) {
      console.writeError(e.getMessage());
    }
  }

  @ShellMethod("Add task to Person")
  public Person addTaskToUser(String sso, String taskId) {
    Person taskHaveUser;
    try {
      taskHaveUser = kanbanManager
          .addTaskToPerson(kanbanManager.getPerson(sso), kanbanManager.getTask(taskId));
      console.write("add task to user: %s get: taskid: %s", taskHaveUser.getName(), taskId);
    } catch (ModelException e) {
      console.writeError(e.getMessage());
      return null;
    }
    return taskHaveUser;
  }

  @ShellMethod("Get task by task id")
  public Task task(String taskId) {
    Task task = null;

    try {
      task = kanbanManager.getTask(taskId);
      console.write(task.toString());
    } catch (NoSuchTaskException e) {
      console.writeError(e.getMessage());
    }
    return task;
  }

  @ShellMethod("Get user by sso id")
  public Person user(String sso) {
    Person person = null;

    try {
      person = kanbanManager.getPerson(sso);
      console.write(person.toString());
    } catch (NoSuchUserException e) {
      console.writeError(e.getMessage());
    }
    return person;
  }

  @ShellMethod("Edit description")
  public Task editTask(String taskId, @ShellOption(defaultValue = " ", value = {"-d",
      "--description"}) String description) {
    Task editedTask;
    try {
      editedTask = kanbanManager.getTask(taskId);
      editedTask.setTaskDescription(description);
    } catch (NoSuchTaskException e) {
      console.writeError(e.getMessage());
      return null;
    }

    Task task;
    try {
      task = kanbanManager.editTask(editedTask);
    } catch (NoSuchTaskException e) {
      console.writeError(e.getMessage());
      return null;
    }

    console.write(task.toString());
    return task;
  }

  @ShellMethod("Edit user name")
  public Person editUser(String sso, @ShellOption(defaultValue = "Unknown", value = {"-n",
      "--name"}) String name) {
    Person editedPerson;
    try {
      editedPerson = kanbanManager.getPerson(sso);
      editedPerson.setName(name);
      console.write("This %s user rename to: %s", sso, name);
    } catch (NoSuchUserException e) {
      console.writeError(e.getMessage());
      return null;
    }

    Person person;
    try {
      person = kanbanManager.editPerson(editedPerson);
    } catch (NoSuchUserException e) {
      console.writeError(e.getMessage());
      return null;
    }

    console.write(person.toString());
    return person;
  }

  @ShellMethod("Delete person")
  public void deleteUser(String sso) {
    try {
      kanbanManager.deletePerson(sso);
      console.write("Successful deleted: %s", sso);
    } catch (ModelException e) {
      console.writeError(e.getMessage());
    }
  }

  @ShellMethod("Delete task")
  public void deleteTask(String taskId) {
    try {
      kanbanManager.deleteTask(taskId);
      console.write("Successful deleted: %s", taskId);
    } catch (ModelException e) {
      console.writeError(e.getMessage());
    }
  }
}
