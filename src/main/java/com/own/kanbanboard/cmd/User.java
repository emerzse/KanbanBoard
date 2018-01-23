package com.own.kanbanboard.cmd;

@FunctionalInterface
public interface User<String, Person> {
Person getUser(String sso);
}
