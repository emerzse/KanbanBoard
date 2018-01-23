package com.own.kanbanboard.exceptions;

public class NoSuchUserException extends ModelException {

  public NoSuchUserException() {
    super();
  }

  public NoSuchUserException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoSuchUserException(Throwable cause) {
    super(cause);
  }

  public NoSuchUserException(String message) {
    super(message);
  }
}
