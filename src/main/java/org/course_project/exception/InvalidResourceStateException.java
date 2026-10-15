package org.course_project.exception;

public class InvalidResourceStateException extends RuntimeException {
  public InvalidResourceStateException(String message) {
    super(message);
  }
}
