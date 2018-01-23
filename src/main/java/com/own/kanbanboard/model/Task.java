package com.own.kanbanboard.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Task implements Model{

  private  String taskId;
  private String taskName;
  private String taskDescription;
  private Status status;
  private State state;
  private List<Person> persons;
  private int effort;
  private int toDo;

  private Task() {
    this.state = State.NEW;
    this.status = Status.NORMAL;
    persons = new ArrayList<>();
  }

  public Task(String id, String taskName) {
    this();
    this.taskId = id;
    this.taskName = taskName;
  }

  public Task(String id, String taskName, String taskDescription) {
    this(id, taskName);
    this.taskDescription = taskDescription;
  }

  public int getEffort() {
    return effort;
  }

  @Override
  public Serializable getId() {
    return taskId;
  }

  public void setEffort(int effort) {
    this.effort = effort;
  }

  public int getToDo() {
    return toDo;
  }

  public void setToDo(int toDo) {
    this.toDo = toDo;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getTaskDescription() {
    return taskDescription;
  }

  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public List<Person> getPersons() {
    return persons;
  }

  public enum State {
    NEW,
    IN_PROGRESS,
    IN_REVIEW,
    IN_PM_REVIEW,
    COMLETED
  }

  public enum Status {
    NORMAL,
    BLOCK,
    READY,
  }

  @Override
  public String toString() {
    return "Task{" +
        "taskId='" + taskId + '\'' +
        ", taskName='" + taskName + '\'' +
        ", taskDescription='" + taskDescription + '\'' +
        ", status=" + status +
        ", state=" + state +
        ", persons=" + persons +
        ", effort=" + effort +
        ", toDo=" + toDo +
        '}';
  }
}

