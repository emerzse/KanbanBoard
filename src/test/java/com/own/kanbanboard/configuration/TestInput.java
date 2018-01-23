package com.own.kanbanboard.configuration;

import java.util.List;
import org.springframework.shell.Input;

public class TestInput implements Input {

  private final String rawText;
  private final List<String> words;

  public TestInput(String rawText, List<String> words) {
    this.rawText = rawText;
    this.words = words;
  }

  @Override
  public String rawText() {
    return rawText;
  }

  @Override
  public List<String> words() {
    return words;
  }
}