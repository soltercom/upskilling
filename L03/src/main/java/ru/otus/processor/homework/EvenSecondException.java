package ru.otus.processor.homework;

public class EvenSecondException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Even second exception.";
    }
}
