package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.Assertions.assertThat;

public class ProcessorExchangeFields11with12Test {

    @Test
    @DisplayName("should exchange the value of the field 11 with value of the field 12")
    void processTest() {
        //given
        var message = new Message.Builder(1L)
            .field1("field1")
            .field2("field2")
            .field3("field3")
            .field6("field6")
            .field10("field10")
            .field11("field11")
            .field12("field12")
            .build();

        var processor = new ProcessorExchangeFields11with12();

        //when
        var newMessage = processor.process(message);

        //then
        assertThat(newMessage.getField11()).isEqualTo("field12");
        assertThat(newMessage.getField12()).isEqualTo("field11");
    }

}
