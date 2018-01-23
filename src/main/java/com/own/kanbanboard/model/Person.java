package com.own.kanbanboard.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Model{

  private String name;
  private String sso;
  private List<Task> tasks;
  private String role;

  private Person(String sso) {
    this.sso = sso;
    this.tasks = new ArrayList<>();
  }

  public Person(String sso, String name) {
    this(sso);
    this.name = name;
  }

  @Override
  public Serializable getId() {
    return sso;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", sso='" + sso + '\'' +
        '}';
  }
}
