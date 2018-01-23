package com.own.kanbanboard.exceptions;

public class NoSuchTaskException extends ModelException {

  public NoSuchTaskException() {
    super();
  }

  public NoSuchTaskException(String message) {
    super(message);
  }

  public NoSuchTaskException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoSuchTaskException(Throwable cause) {
    super(cause);
  }
}
