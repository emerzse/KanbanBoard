package com.own.kanbanboard.services.impl;

import com.own.kanbanboard.dao.PersonDao;
import com.own.kanbanboard.dao.TaskDao;
import com.own.kanbanboard.exceptions.DuplicatedTaskException;
import com.own.kanbanboard.exceptions.ModelException;
import com.own.kanbanboard.exceptions.NoSuchTaskException;
import com.own.kanbanboard.exceptions.NoSuchUserException;
import com.own.kanbanboard.model.Person;
import com.own.kanbanboard.model.Task;
import com.own.kanbanboard.model.Task.State;
import com.own.kanbanboard.services.KanbanManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KanbanManagerImpl implements KanbanManager {

  private final PersonDao personDao;
  private final TaskDao taskDao;

  @Autowired
  public KanbanManagerImpl(PersonDao personDao, TaskDao taskDao) {
    this.personDao = personDao;
    this.taskDao = taskDao;
  }

  @Override
  public Person addPerson(String sso, String name) throws ModelException {
    return personDao.add(new Person(sso, name));
  }

  @Override
  public Person editPerson(Person editedPerson) throws NoSuchUserException {
    return personDao.edit(editedPerson);

  }

  @Override
  public void deletePerson(String sso) throws ModelException {
    personDao.delete(sso);
  }

  @Override
  public Person getPerson(Serializable sso) throws NoSuchUserException {
    return personDao.get(sso);
  }

  @Override
  public List<Person> listAllPersons() {
    return personDao.list();
  }

  @Override
  public Task addTask(String taskId, String name, String description) throws ModelException {
    if (description.isEmpty()) {
      return taskDao.add(new Task(taskId, name));
    }
    return taskDao.add(new Task(taskId, name, description));
  }

  @Override
  public Task addTask(String taskId, String name) throws ModelException {
    return taskDao.add(new Task(taskId, name));
  }

  @Override
  public Person addTaskToPerson(Person person, Task task) throws ModelException {
    if (person.getTasks().contains(task) && task.getPersons().contains(person)) {
      throw new DuplicatedTaskException("The user already has this task");
    }
    person.getTasks().add(task);
    task.getPersons().add(person);

    editTask(task);
    return editPerson(person);
  }

  @Override
  public Task editTask(Task editedTask) throws NoSuchTaskException {
    return taskDao.edit(editedTask);

  }

  @Override
  public void deleteTask(String taskId) throws NoSuchTaskException {
    taskDao.delete(taskId);
  }

  @Override
  public Task getTask(Serializable taskId) throws NoSuchTaskException {
    return taskDao.get(taskId);
  }

  @Override
  public List<Task> listAllTask() {
    return taskDao.list();
  }

  @Override
  public List<Task> listTaskBySSO(String sso) {
    List<Task> filerTaskList;
    filerTaskList = listAllTask().stream().filter(task -> task.getPersons().stream()
        .anyMatch(person -> sso.equals(String.valueOf(person.getId())))).collect(
        Collectors.toList());

    if (filerTaskList == null) {
      filerTaskList = new ArrayList<>();
    }

    return filerTaskList;
  }

  @Override
  public List<Task> listTaskByState(State state) {
    List<Task> filerTaskList;

    filerTaskList = listAllTask().stream().filter(task -> state.equals(task.getState())).collect(
        Collectors.toList());

    if (filerTaskList == null) {
      filerTaskList = new ArrayList<>();
    }

    return filerTaskList;
  }
}
