package com.own.kanbanboard;

import com.own.kanbanboard.services.ConsoleService;
import com.own.kanbanboard.services.KanbanManager;
import com.own.kanbanboard.services.impl.KanbanManagerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class KanbanContextFactory {

  private KanbanContextFactory() {
  }

  public static ApplicationContext getContext() {
    return new AnnotationConfigApplicationContext("com.ge.kanbanboard");
  }

  public static KanbanManager getKanbanManagerService() {
    return getContext().getBean("kanbanManagerImpl", KanbanManagerImpl.class);
  }

  public static ConsoleService getConsoleService() {
    return getContext().getBean("consoleService", ConsoleService.class);
  }
}
