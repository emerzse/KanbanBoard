package com.own.kanbanboard;

import com.own.kanbanboard.dao.PersonDao;
import com.own.kanbanboard.dao.TaskDao;
import com.own.kanbanboard.datastore.DataStorage;
import com.own.kanbanboard.datastore.ModelStore;
import com.own.kanbanboard.services.KanbanManager;
import com.own.kanbanboard.services.impl.KanbanManagerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

/*  @Bean
  public DataStorage<Task> taskDataStorage(){
    return new ModelStore<>();
  }

  @Bean
  public DataStorage<Person> personDataStorage(){
    return new ModelStore<>();
  }*/

  @Bean
  @Scope("prototype")
  public DataStorage personDataStorage() {
    return new ModelStore<>();
  }

  @Bean
  public KanbanManager kanbanManager(PersonDao personDao, TaskDao taskDao) {
    return new KanbanManagerImpl(personDao,taskDao);
  }
}
