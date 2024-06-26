package com.taskTracker.api;

import com.taskTracker.dto.TaskDto;
import com.taskTracker.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HomeRestControllerTest {

    @Mock
    private TaskServiceImpl taskServiceImpl;

    @InjectMocks
    private HomeRestController homeRestController;

    private LocalValidatorFactoryBean validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
    }

    @Test
    void getTasks_ReturnsListOfTasks() {
        // given
        List<TaskDto> expectedTasks = new ArrayList<>();
        when(taskServiceImpl.getAllTasks()).thenReturn(expectedTasks);

        // when
        List<TaskDto> result = homeRestController.getTasks();

        // then
        assertEquals(expectedTasks, result);
        verify(taskServiceImpl, times(1)).getAllTasks();
    }

    @Test
    void addTask_ValidTask_ReturnsSuccessMessage() {
        // given
        TaskDto taskDto = new TaskDto();
        taskDto.setName("Test task");
        taskDto.setDescription("Test description");

        // when
        String result = homeRestController.addTask(taskDto);

        // then
        assertEquals("Task added successfully.", result);
        verify(taskServiceImpl, times(1)).addTask(taskDto);
    }

    @Test
    void addTask_ThrowsDateTimeException_ReturnsErrorMessage() {
        // given
        TaskDto taskDto = new TaskDto();
        taskDto.setName("Test task");
        taskDto.setDescription("Test description");

        doThrow(new DateTimeException("Invalid date")).when(taskServiceImpl).addTask(taskDto);

        // when
        String result = homeRestController.addTask(taskDto);

        // then
        assertEquals("Invalid date", result);
        verify(taskServiceImpl, times(1)).addTask(taskDto);
    }
}
