package org.course_project.exception;

public class CardLimitExceededException extends RuntimeException {
  public CardLimitExceededException(String message) {
    super(message);
  }
}
