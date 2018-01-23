package com.own.kanbanboard.services;

import com.own.kanbanboard.exceptions.DuplicatedTaskException;
import com.own.kanbanboard.exceptions.ModelException;
import com.own.kanbanboard.exceptions.NoSuchTaskException;
import com.own.kanbanboard.exceptions.NoSuchUserException;
import com.own.kanbanboard.model.Person;
import com.own.kanbanboard.model.Task;
import com.own.kanbanboard.model.Task.State;
import java.io.Serializable;
import java.util.List;

public interface KanbanManager {
   Person addPerson(String sso, String name) throws ModelException;
   Person editPerson(Person editedPerson) throws NoSuchUserException;
   void deletePerson(String sso) throws ModelException;
   Person getPerson(Serializable sso) throws NoSuchUserException;
   List<Person> listAllPersons();
   Task addTask(String taskId, String name) throws DuplicatedTaskException, ModelException;
   Task addTask(String taskId, String name, String description) throws DuplicatedTaskException, ModelException;
   Person addTaskToPerson(Person person, Task task) throws ModelException;
   Task editTask(Task editedTask) throws NoSuchTaskException;
   void deleteTask(String taskId) throws NoSuchTaskException;
   Task getTask(Serializable taskId) throws NoSuchTaskException;
   List<Task> listAllTask();
   List<Task> listTaskBySSO(String sso);
   List<Task> listTaskByState(State state);
}
