package com.faithabiola.activity_tracker.services;

import com.faithabiola.activity_tracker.dto.TaskCreationDto;
import com.faithabiola.activity_tracker.utils.ApiResponse;

public interface TaskService {
    ApiResponse createTask(TaskCreationDto taskCreationDto);

    ApiResponse viewTask(String userUuid, String title);

    ApiResponse viewAllUserTask(String userUuid);

    ApiResponse deleteTask(String userUuid, String title);

    ApiResponse viewUserInProgressTasks(String userUuid);

    ApiResponse viewUserPendingTasks(String userUuid);

    ApiResponse viewUserDoneTasks(String userUuid);

    ApiResponse makeTaskPending(String userUuid, String title);

    ApiResponse makeTaskDone(String userUuid, String title);

    ApiResponse makeTaskInProgress(String userUuid, String title);
}
