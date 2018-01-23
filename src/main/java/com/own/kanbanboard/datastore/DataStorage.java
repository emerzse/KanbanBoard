package com.own.kanbanboard.datastore;

import com.own.kanbanboard.model.Model;
import java.io.Serializable;
import java.util.List;

public interface DataStorage <T extends Model>{

  T get(Serializable id);

  T store(T model);

  void delete(Serializable serializable);

  List<T> findAll();
}
