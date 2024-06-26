package com.taskTracker.api;

import com.taskTracker.dto.TaskDto;
import com.taskTracker.exception.TaskDoesNotExistException;
import com.taskTracker.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskRestControllerTest {

    @Mock
    private TaskServiceImpl taskServiceImpl;

    @InjectMocks
    private TaskRestController taskRestController;

    public TaskRestControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTasks_ReturnsTasksData() {
        // when
        String result = taskRestController.getTasks();

        // then
        assertEquals("Tasks:\n", result);
    }

    @Test
    void getTaskDetails_ExistingTaskId_ReturnsTaskDetails() {
        // given
        Long taskId = 1L;
        String expectedDetails = "Task details for ID: " + taskId + "\nnull";

        // when
        String result = taskRestController.getTaskDetails(taskId);

        // then
        assertEquals(expectedDetails, result);
    }

    @Test
    void getTaskDetails_NonExistingTaskId_ReturnsErrorMessage() throws TaskDoesNotExistException {
        // given
        Long taskId = 2L;
        doThrow(new TaskDoesNotExistException("Task with ID " + taskId + " does not exist."))
                .when(taskServiceImpl).getTaskInfo(taskId);

        // when
        String result = taskRestController.getTaskDetails(taskId);

        // then
        assertEquals("Task with ID 2 does not exist.", result);
    }

    @Test
    void addTask_ValidTask_ReturnsSuccessMessage() {
        // given
        TaskDto taskDto = new TaskDto();

        // when
        String result = taskRestController.addTask(taskDto);

        // then
        assertEquals("Task added successfully.", result);
        verify(taskServiceImpl, times(1)).addTask(taskDto);
    }
}
