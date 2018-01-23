package com.own.kanbanboard.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestApplicationRunner  implements ApplicationRunner{

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("Done");
  }
}
