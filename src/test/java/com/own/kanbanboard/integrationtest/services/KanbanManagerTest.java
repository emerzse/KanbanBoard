package com.own.kanbanboard.integrationtest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.own.kanbanboard.KanbanBoardAplication;
import com.own.kanbanboard.KanbanContextFactory;
import com.own.kanbanboard.configuration.TestApplicationRunner;
import com.own.kanbanboard.configuration.TestInput;
import com.own.kanbanboard.exceptions.DuplicatedTaskException;
import com.own.kanbanboard.exceptions.ModelException;
import com.own.kanbanboard.exceptions.NoSuchTaskException;
import com.own.kanbanboard.model.Person;
import com.own.kanbanboard.model.Task;
import com.own.kanbanboard.model.Task.State;
import com.own.kanbanboard.services.KanbanManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.Input;
import org.springframework.shell.Shell;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KanbanBoardAplication.class)
@Import(TestApplicationRunner.class)
public class KanbanManagerTest {

  private KanbanManager kanbanManager = KanbanContextFactory.getKanbanManagerService();

  @Autowired
  private Shell shell;

  @Test
  public void addPersonTest() throws ModelException {
    Person result = (Person) shell.evaluate(() -> "new-user 111111225 TestUser1");

    assertNotNull(result);
    assertEquals("TestUser1", result.getName());
    assertEquals("111111225", result.getId());
  }

  @Test
  public void addTaskToPersonTest() {
    shell.evaluate(() -> "new-user 111111111 TestUser");
    Task task = (Task) shell.evaluate(input("new-task", "33331", "Add task to employer"));

    Person result = (Person) shell.evaluate(() -> "add-task-to-user 111111111 33331");

    assertNotNull(result);
    assertEquals("TestUser", result.getName());
    assertEquals("111111111", result.getId());
    assertEquals("33331", result.getTasks().get(0).getId());
    assertEquals("111111111", task.getPersons().get(0).getId());

  }

  @Test
  public void addPersonDuplicatedTest() throws ModelException {
    shell.evaluate(() -> "new-user 111111111 TestUser");

    Person result = (Person) shell.evaluate(() -> "new-user 111111111 TestUser");

    assertNull(result);
  }

  @Test
  public void deletePersonTest() {
    shell.evaluate(() -> "new-user 456823971 TestUser");
    Task task = (Task) shell.evaluate(input("new-task", "3333", "Add task to employee"));
    shell.evaluate(() -> "add-task-to-user 456823971 3333");

    shell.evaluate(() -> "delete-user 456823971");

    Person result = (Person) shell.evaluate(() -> "user 456823971");
    assertNull(result);
    assertTrue(task.getPersons().isEmpty());
  }

  @Test
  public void editPersonTest() throws ModelException {
    Person person = (Person) shell.evaluate(() -> "new-user 212456665 User1");
    person.setName("Small Doggy");

    Person result = (Person) shell.evaluate(input("edit-user", "212456665", "Small Doggy"));

    assertEquals("Small Doggy", result.getName());
    assertEquals("212456665", result.getId());
  }

  @Test
  public void listPersonsTest() throws ModelException {
    kanbanManager.addPerson("123455555", "User1");
    kanbanManager.addPerson("567811111", "User2");
    kanbanManager.addPerson("987622222", "User3");
    kanbanManager.addPerson("543277777", "User4");

    List<Person> result = kanbanManager.listAllPersons();

    assertNotNull(result);
    assertTrue(!result.isEmpty());
    assertTrue(result.stream().anyMatch(person -> "User1".equals(person.getName())));
  }

  @Test
  public void addTaskTest() throws ModelException {
    Task task = kanbanManager.addTask("12455", "TaskName1");
    assertNotNull(task);
    assertEquals("TaskName1", task.getTaskName());
    assertEquals("12455", task.getId());
  }

  @Test(expected = DuplicatedTaskException.class)
  public void addTaskDuplicatedTest() throws ModelException {
    kanbanManager.addTask("124552", "TaskName");
    kanbanManager.addTask("124552", "TaskName");
  }

  @Test
  public void addTaskToUser() throws ModelException {
    Person person = kanbanManager.addPerson("000000000", "TestHero");
    Task task = kanbanManager.addTask("000122", "This is a Task");

    kanbanManager.addTaskToPerson(person, task);
    Task result = kanbanManager.getTask(task.getId());

    assertEquals(person, result.getPersons().get(0));
    assertEquals(result, person.getTasks().get(0));
  }

  @Test(expected = NoSuchTaskException.class)
  public void deleteTaskTest() throws ModelException {
    Person person = kanbanManager.addPerson("587123444", "testy");
    Task task = kanbanManager.addTask("134345", "TaskName");
    kanbanManager.addTaskToPerson(person, task);

    kanbanManager.deleteTask("134345");

    assertNull(kanbanManager.getTask(task.getId()));
    assertTrue(person.getTasks().isEmpty());
  }

  @Test
  public void editTaskTest() throws ModelException {
    Person person = kanbanManager.addPerson("666335587", "testy");
    Task task = kanbanManager.addTask("44444", "TaskName");
    kanbanManager.addTaskToPerson(person, task);

    task.setTaskName("Small Doggy task");
    task.getPersons().clear();
    Task result = kanbanManager.editTask(task);

    assertEquals("Small Doggy task", result.getTaskName());
    assertEquals("44444", result.getId());
    assertTrue(result.getPersons().isEmpty());
  }

  @Test
  public void listTaskTest() throws ModelException {
    kanbanManager.addTask("1234", "Task1");
    kanbanManager.addTask("5678", "Task2");
    kanbanManager.addTask("9876", "Task3");
    kanbanManager.addTask("5432", "Task4");

    List<Task> result = kanbanManager.listAllTask();

    assertNotNull(result);
    assertTrue(!result.isEmpty());
    assertTrue(result.stream().anyMatch(task -> "Task1".equals(task.getTaskName())));
  }

  @Test
  public void listTaskBySsoTest() throws ModelException {
    Person person1 = kanbanManager.addPerson("123659744", "Strong");
    Person person2 = kanbanManager.addPerson("123658745", "Weak");
    Task task1 = kanbanManager.addTask("0000", "Task1");
    kanbanManager.addTaskToPerson(person1, task1);
    Task task2 = kanbanManager.addTask("1111", "Task2");
    kanbanManager.addTaskToPerson(person1, task2);
    Task task3 = kanbanManager.addTask("2222", "Task3");
    kanbanManager.addTaskToPerson(person1, task3);
    Task task4 = kanbanManager.addTask("3333", "Task4");
    kanbanManager.addTaskToPerson(person2, task4);

    List<Task> result1 = kanbanManager.listTaskBySSO(String.valueOf(person1.getId()));
    List<Task> result2 = kanbanManager.listTaskBySSO(String.valueOf(person2.getId()));

    assertNotNull(result1);
    assertNotNull(result2);
    assertTrue(!result1.isEmpty());
    assertTrue(!result2.isEmpty());
    assertEquals(3, result1.size());
    assertEquals(1, result2.size());
  }

  @Test
  public void listTaskByStateTest() throws ModelException {
    Task task1 = kanbanManager.addTask("0000", "Task1");
    task1.setState(State.IN_PROGRESS);
    Task task2 = kanbanManager.addTask("1111", "Task2");
    task2.setState(State.IN_PROGRESS);
    Task task3 = kanbanManager.addTask("2222", "Task3");
    task3.setState(State.IN_PROGRESS);
    Task task4 = kanbanManager.addTask("3333", "Task4");
    task4.setState(State.COMLETED);

    List<Task> result1 = kanbanManager.listTaskByState(State.IN_PROGRESS);
    List<Task> result2 = kanbanManager.listTaskByState(State.COMLETED);

    assertNotNull(result1);
    assertNotNull(result2);
    assertTrue(!result1.isEmpty());
    assertTrue(!result2.isEmpty());
    assertEquals(3, result1.size());
    assertEquals(1, result2.size());
  }

  private Input input(String command, String... parameters) {
    String rawText = command;

    for (String parameter : parameters) {
      rawText = rawText.concat(" " + parameter);
    }

    List<String> parameterList = new ArrayList<>();

    parameterList.add(command);
    parameterList.addAll(Arrays.asList(parameters));

    return new TestInput(
        rawText, parameterList);

  }

}
