package com.own.kanbanboard.dao;

import com.own.kanbanboard.exceptions.ModelException;
import com.own.kanbanboard.model.Model;
import java.io.Serializable;
import java.util.List;

public interface Dao <T extends Model>{

  T get(Serializable id) throws ModelException;
  T add(T model) throws ModelException;
  T edit(T model) throws ModelException;
  void delete(Serializable id) throws ModelException;
  List<T> list();

}
