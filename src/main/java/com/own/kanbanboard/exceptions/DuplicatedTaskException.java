package com.own.kanbanboard.exceptions;

public class DuplicatedTaskException extends ModelException{

  public DuplicatedTaskException() {
    super();
  }

  public DuplicatedTaskException(String message) {
    super(message);
  }

  public DuplicatedTaskException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicatedTaskException(Throwable cause) {
    super(cause);
  }
}
