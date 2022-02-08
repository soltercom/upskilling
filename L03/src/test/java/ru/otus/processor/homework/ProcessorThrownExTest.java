package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ProcessorThrownExTest {

    @Test
    @DisplayName("#process() should throw an exception EvenSecondException every even second")
    void evenSecondExceptionTest() {
        //given
        var message = new Message.Builder(1L).build();

        var dateTimeProvider = mock(DateTimeProviderImpl.class);
        when(dateTimeProvider.getSecond()).thenReturn(2);

        var processorThrowException = new ProcessorThrownEx(dateTimeProvider);

        // when
        assertThatExceptionOfType(EvenSecondException.class)
                .isThrownBy(() -> processorThrowException.process(message));

        //then
        verify(dateTimeProvider, times(1)).getSecond();
    }

    @Test
    @DisplayName("#process() should not throw an exception EvenSecondException every odd second")
    void oddSecondExceptionTest() {
        //given
        var message = new Message.Builder(1L).build();

        var dateTimeProvider = mock(DateTimeProviderImpl.class);
        when(dateTimeProvider.getSecond()).thenReturn(1);

        var processorThrowException = new ProcessorThrownEx(dateTimeProvider);

        // when
        assertThatCode(() -> processorThrowException.process(message))
                .doesNotThrowAnyException();

        //then
        verify(dateTimeProvider, times(1)).getSecond();
    }

}
