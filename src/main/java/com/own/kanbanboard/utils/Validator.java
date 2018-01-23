package com.own.kanbanboard.utils;

import com.own.kanbanboard.exceptions.InvalidIdException;
import java.io.Serializable;

public final class Validator {

  private Validator() {
  }

  public static void ssoIdIsInvalid(Serializable sso) throws InvalidIdException {
    String id = String.valueOf(sso);

    StringBuilder errorCollection = new StringBuilder();
    if (id.length() != 9) {
      errorCollection.append("Invalid length, sso must be 9 length and now is:").append(id.length())
          .append(" ;\n");
    }

    if (!id.matches("\\d+")) {
      errorCollection.append("Invalid characters, sso must be use numeric;\n");
    }

    if (errorCollection.length() > 0) {
      throw new InvalidIdException("This sso id is invalid: \n".concat(errorCollection.toString()));
    }
  }

}
