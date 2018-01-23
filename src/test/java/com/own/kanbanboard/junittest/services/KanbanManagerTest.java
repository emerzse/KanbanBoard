package com.own.kanbanboard.junittest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.own.kanbanboard.exceptions.DuplicatedTaskException;
import com.own.kanbanboard.exceptions.DuplicatedUserException;
import com.own.kanbanboard.exceptions.ModelException;
import com.own.kanbanboard.model.Person;
import com.own.kanbanboard.model.Task;
import com.own.kanbanboard.model.Task.State;
import com.own.kanbanboard.services.KanbanManager;
import com.own.kanbanboard.services.impl.KanbanManagerImpl;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class KanbanManagerTest {

  private KanbanManager kanbanManager = new KanbanManagerImpl(null, null);

  @Test
  public void addPersonTest() throws ModelException {
    Person person = kanbanManager.addPerson("9879657", "TestUser");
    Task task = kanbanManager.addTask("33331", "Add task to employer");
    Person result = kanbanManager.addTaskToPerson(person, task);

    assertNotNull(result);
    assertEquals("TestUser", result.getName());
    assertEquals("9879657", result.getId());
    assertEquals("33331", result.getTasks().get(0).getId());
    assertEquals("9879657", task.getPersons().get(0).getId());
  }

  @Test(expected = DuplicatedUserException.class)
  public void addPersonDuplicatedTest() throws ModelException {
    kanbanManager.addPerson("212455", "TestUser");
    kanbanManager.addPerson("212455", "TestUser");
  }

  @Test
  public void deletePersonTest() throws ModelException {
    Person person = kanbanManager.addPerson("212455", "TestUser");
    Task task = kanbanManager.addTask("3333", "Add task to employee");
    kanbanManager.addTaskToPerson(person, task);

    kanbanManager.deletePerson("212455");

    Person result = kanbanManager.getPerson(person.getId());
    assertNull(result);
    assertTrue(task.getPersons().isEmpty());
  }

  @Test
  public void editPersonTest() throws ModelException {
    Person person = kanbanManager.addPerson("21245", "User1");
    person.setName("Small Doggy");

    Person result = kanbanManager.editPerson(person);

    assertEquals("Small Doggy", result.getName());
    assertEquals("21245", result.getId());
  }

  @Test
  public void listPersonsTest() throws ModelException {
    kanbanManager.addPerson("1234", "User1");
    kanbanManager.addPerson("5678", "User2");
    kanbanManager.addPerson("9876", "User3");
    kanbanManager.addPerson("5432", "User4");

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
    Person person = kanbanManager.addPerson("00000", "TestHero");
    Task task = kanbanManager.addTask("000122", "This is a Task");

    kanbanManager.addTaskToPerson(person, task);
    Task result = kanbanManager.getTask(task.getId());

    assertEquals(person, result.getPersons().get(0));
    assertEquals(result, person.getTasks().get(0));
  }

  @Test
  public void deleteTaskTest() throws ModelException {
    Person person = kanbanManager.addPerson("123444", "testy");
    Task task = kanbanManager.addTask("134345", "TaskName");
    kanbanManager.addTaskToPerson(person, task);

    kanbanManager.deleteTask("134345");

    Task result = kanbanManager.getTask(task.getId());
    assertNull(result);
    assertTrue(person.getTasks().isEmpty());
  }

  @Test
  public void editTaskTest() throws ModelException {
    Person person = kanbanManager.addPerson("666335", "testy");
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
    Person person1 = kanbanManager.addPerson("1236598744","Strong");
    Person person2 = kanbanManager.addPerson("1236598745","Weak");
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
}
