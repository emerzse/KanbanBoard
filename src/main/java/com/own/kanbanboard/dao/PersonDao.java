package com.own.kanbanboard.dao;

import com.own.kanbanboard.datastore.DataStorage;
import com.own.kanbanboard.exceptions.DuplicatedUserException;
import com.own.kanbanboard.exceptions.ModelException;
import com.own.kanbanboard.exceptions.NoSuchUserException;
import com.own.kanbanboard.model.Person;
import com.own.kanbanboard.utils.Validator;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonDao implements Dao<Person> {

  private final DataStorage<Person> storage;

  @Autowired
  public PersonDao(DataStorage<Person> storage) {
    this.storage = storage;
  }

  @Override
  public Person get(Serializable id) throws NoSuchUserException {
    Person person = storage.get(id);

    if (person == null) {
      throw new NoSuchUserException("No such this person");
    }
    return person;
  }

  @Override
  public Person add(Person model) throws ModelException {
    Serializable id = model.getId();
    Validator.ssoIdIsInvalid(id);
    if (check(id)) {
      throw new DuplicatedUserException("This person is already");
    }

    return storage.store(model);
  }

  private boolean check(Serializable id) throws ModelException {
    return storage.get(id) != null;
  }

  @Override
  public Person edit(Person model) throws NoSuchUserException {
    if (get(model.getId()) == null) {
      throw new NoSuchUserException("No such this person");
    }
    return storage.store(model);
  }

  @Override
  public void delete(Serializable id) throws ModelException {
    Validator.ssoIdIsInvalid(id);
    Person deletePerson = get(id);
    if (deletePerson == null) {
      throw new NoSuchUserException("No such this person");
    }

    deletePerson.getTasks()
        .forEach(task -> task.getPersons()
            .removeIf(person -> deletePerson.getId().equals(person.getId())));

    storage.delete(id);
  }

  @Override
  public List<Person> list() {
    return storage.findAll();
  }

}
