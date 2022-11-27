package com.faithabiola.activity_tracker.controllers;

import com.faithabiola.activity_tracker.dto.TaskCreationDto;
import com.faithabiola.activity_tracker.services.TaskService;
import com.faithabiola.activity_tracker.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vi/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create_task")
    public ApiResponse signUp(@RequestBody TaskCreationDto taskCreationDto) {
        return taskService.createTask(taskCreationDto);
    }

    @GetMapping("/find_task/{userUuid}/{title}")
    public ApiResponse findTaskPath(@PathVariable String userUuid, @PathVariable String title) {
        return taskService.viewTask(userUuid, title);
    }

    @GetMapping("/get_all_tasks/{uuid}")
    public ApiResponse getAllTasks(@PathVariable("uuid") String userUuid) {
        return taskService.viewAllUserTask(userUuid);
    }

    @DeleteMapping("/delete_task/{uuid}/{title}")
    public ApiResponse deleteTask(@PathVariable("uuid") String userUuid, @PathVariable String title) {
        return taskService.deleteTask(userUuid, title);
    }

    @GetMapping("/get_in_progress_tasks/{uuid}")
    public ApiResponse getInProgressTasks(@PathVariable("uuid") String userUuid) {
        return taskService.viewUserInProgressTasks(userUuid);
    }

    @GetMapping("/get_pending_tasks/{uuid}")
    public ApiResponse getPendingTasks(@PathVariable("uuid") String userUuid) {
        return taskService.viewUserPendingTasks(userUuid);
    }

    @GetMapping("/get_done_tasks/{uuid}")
    public ApiResponse getDoneTasks(@PathVariable("uuid") String userUuid) {
        return taskService.viewUserDoneTasks(userUuid);
    }

    @PostMapping("/make_task_pending/{uuid}/{title}")
    public ApiResponse makeTaskPending(@PathVariable String uuid, @PathVariable String title) {
        return taskService.makeTaskPending(uuid, title);
    }

    @PostMapping("/make_task_done/{uuid}/{title}")
    public ApiResponse makeTaskDone(@PathVariable String uuid, @PathVariable String title) {
        return taskService.makeTaskDone(uuid, title);
    }

    @PostMapping("/make_task_in_progress/{uuid}/{title}")
    public ApiResponse makeTaskInProgress(@PathVariable String uuid, @PathVariable String title) {
        return taskService.makeTaskInProgress(uuid, title);
    }

}
