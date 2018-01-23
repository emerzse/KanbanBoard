package com.own.kanbanboard.dao;

import com.own.kanbanboard.datastore.DataStorage;
import com.own.kanbanboard.exceptions.DuplicatedTaskException;
import com.own.kanbanboard.exceptions.ModelException;
import com.own.kanbanboard.exceptions.NoSuchTaskException;
import com.own.kanbanboard.model.Task;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskDao implements Dao<Task> {

  private final DataStorage<Task> storage;

  @Autowired
  public TaskDao(DataStorage<Task> storage) {
    this.storage = storage;
  }

  @Override
  public Task get(Serializable id) throws NoSuchTaskException {
    Task task = storage.get(id);
    if(task == null) {
      throw new NoSuchTaskException("No such this task");
    }
    return task;
  }

  @Override
  public Task add(Task model) throws ModelException {
    if (check(model.getId())) {
      throw new DuplicatedTaskException("This task is already");
    }
    return storage.store(model);
  }

  @Override
  public Task edit(Task model) throws NoSuchTaskException {
    if (get(model.getId()) == null) {
      throw new NoSuchTaskException("No such this task");
    }
    return storage.store(model);
  }

  private boolean check(Serializable id) throws ModelException {
    return storage.get(id) != null;
  }

  @Override
  public void delete(Serializable id) throws NoSuchTaskException {
    Task deletedTask = get(id);
    if (deletedTask == null) {
      throw new NoSuchTaskException("No such this task");
    }

    deletedTask.getPersons().forEach(
        person -> person.getTasks().remove(deletedTask));

    storage.delete(id);
  }

  @Override
  public List<Task> list() {
    return storage.findAll();
  }
}
