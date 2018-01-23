package com.own.kanbanboard.exceptions;

public class InvalidIdException extends ModelException {

  public InvalidIdException() {
    super();
  }

  public InvalidIdException(String message) {
    super(message);
  }

  public InvalidIdException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidIdException(Throwable cause) {
    super(cause);
  }
}
