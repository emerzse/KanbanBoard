package com.own.kanbanboard.services;

import java.io.PrintStream;
import org.springframework.stereotype.Service;

@Service
public class ConsoleService {
  private final PrintStream out = System.out;

  public void write(String msg, String... args) {
    write(Color.YELLOW, msg, args);
  }

  public void writeError(String msg, String... args) {
    write(Color.RED, msg, args);
  }

  private void write(Color color, String msg, String... args) {
    this.out.print("> ");
    this.out.print(color.colorCode);
    this.out.printf(msg, (Object[]) args);
    this.out.print(Color.RESET.colorCode);
    this.out.println();
  }

  enum Color {
    YELLOW("\u001B[33m"),
    RED("\u001B[31m"),
    RESET("\u001B[0m");

    private String colorCode;

    Color(String colorCode) {
      this.colorCode = colorCode;
    }
  }
}
