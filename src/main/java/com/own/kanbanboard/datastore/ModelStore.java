package com.own.kanbanboard.datastore;

import com.own.kanbanboard.model.Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ModelStore<T extends Model> implements DataStorage<T> {

  private final Map<Serializable, T> storage = new HashMap<>();

  @Override
  public T get(Serializable id) {
    return storage.get(id);
  }

  @Override
  public T store(T model) {
    storage.put(model.getId(), model);

    return model;
  }

  @Override
  public void delete(Serializable serializable) {
    storage.remove(serializable);
  }

  @Override
  public List<T> findAll() {
    return new ArrayList<>(storage.values());
  }

}
