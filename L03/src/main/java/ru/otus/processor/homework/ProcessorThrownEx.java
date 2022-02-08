package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorThrownEx implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrownEx(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if (dateTimeProvider.getSecond() % 2 == 0) {
            throw new EvenSecondException();
        }
        return message;
    }
}
