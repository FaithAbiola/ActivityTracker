package com.faithabiola.activity_tracker.services.serviceImpl;

import com.faithabiola.activity_tracker.dto.TaskCreationDto;
import com.faithabiola.activity_tracker.dto.TaskResponseDto;
import com.faithabiola.activity_tracker.entities.Task;
import com.faithabiola.activity_tracker.entities.User;
import com.faithabiola.activity_tracker.repositories.TaskRepository;
import com.faithabiola.activity_tracker.repositories.UserRepository;
import com.faithabiola.activity_tracker.services.TaskService;
import com.faithabiola.activity_tracker.utils.ApiResponse;
import com.faithabiola.activity_tracker.utils.ResponseManager;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final ResponseManager<TaskResponseDto> responseManager;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;


    @Override
    public ApiResponse createTask(TaskCreationDto taskCreationDto) {

        if(taskCreationDto.getTitle() == null)
            return responseManager.error("Title is required");
        if(taskCreationDto.getDescription() == null)
            return responseManager.error("Description is required");
        if(taskCreationDto.getUuid() == null)
            return responseManager.error("User uuid is required");

        boolean titleExist = taskRepository.existsByTitle(taskCreationDto.getTitle());
        if(titleExist)
            return responseManager.error("Title already exists");

        User user = userRepository.findUserByUuid(taskCreationDto.getUuid());
        if(user != null){
            taskCreationDto.setUser(user);
            Task task = mapper.map(taskCreationDto, Task.class);
            taskRepository.save(task);

            TaskResponseDto responseDto = mapper.map(task, TaskResponseDto.class);
            return responseManager.success(responseDto);
        }
        return responseManager.error("User not found");
     }

    @Override
    public ApiResponse viewTask(String userUuid, String title) {
        User user = userRepository.findUserByUuid(userUuid);
        if (title == null)
            return responseManager.error("You've not entered your task title");
        if (user != null) {
            Task task = taskRepository.findTaskByTitle(title);
            TaskResponseDto taskResponse = mapper.map(task, TaskResponseDto.class);
            return responseManager.success(taskResponse);
        }
        return responseManager.error("User does not exist");
    }

    @Override
    public ApiResponse viewAllUserTask(String userUuid) {
        ResponseManager<List<TaskResponseDto>> rm = new ResponseManager<>();
        User user = userRepository.findUserByUuid(userUuid);
        if (user != null) {
            List<TaskResponseDto> responseDtoList = new ArrayList<>();
            List<Task> allTasksByUser = taskRepository.findAllTasksByUserUuid(userUuid);
            if (allTasksByUser != null) {
                allTasksByUser.forEach(task -> {
                    TaskResponseDto responseDto = mapper.map(task, TaskResponseDto.class);
                    responseDtoList.add(responseDto);
                });
                if (responseDtoList.size() == 0) {
                    return responseManager.error("No task created yet");
                }
                return rm.success(responseDtoList);
            }
        }
        return responseManager.error("User with " + userUuid + " not found");
    }

    @Override
    public ApiResponse deleteTask(String userUuid, String title) {
        User user = userRepository.findUserByUuid(userUuid);
        if (title == null)
            return responseManager.error("You have not specified the task to delete");
        if (user != null) {
            int del = taskRepository.deleteTaskByTitle(title);
            if (del < 0)
                return responseManager.error("No such task");
            return responseManager.success(null);
        } else {
            return responseManager.error("You're not a user");
        }
    }

    @Override
    public ApiResponse viewUserInProgressTasks(String userUuid) {
        ResponseManager<List<TaskResponseDto>> rm = new ResponseManager<>();
        User user = userRepository.findUserByUuid(userUuid);
        if (user != null) {
            List<Task> tasks = taskRepository.viewUserInProgressTasks(userUuid);
            if (tasks.size() > 0) {
                List<TaskResponseDto> responseDtoList = new ArrayList<>();
                tasks.forEach(task -> {
                    TaskResponseDto map = mapper.map(task, TaskResponseDto.class);
                    responseDtoList.add(map);
                });
                return rm.success(responseDtoList);
            }
            return responseManager.error("No task in IN_PROGRESS category");
        }
        return responseManager.error("You are not a user");
    }

    @Override
    public ApiResponse viewUserPendingTasks(String userUuid) {
        ResponseManager<List<TaskResponseDto>> rm = new ResponseManager<>();
        User user = userRepository.findUserByUuid(userUuid);
        if (user != null) {
            List<Task> tasks = taskRepository.viewUserPendingTasks(userUuid);
            if (tasks.size() > 0) {
                List<TaskResponseDto> responseDtoList = new ArrayList<>();
                tasks.forEach(task -> {
                    TaskResponseDto map = mapper.map(task, TaskResponseDto.class);
                    responseDtoList.add(map);
                });
                return rm.success(responseDtoList);
            }
            return responseManager.error("No task in PENDING category");
        }
        return responseManager.error("You are not a user");
    }

    @Override
    public ApiResponse viewUserDoneTasks(String userUuid) {
        ResponseManager<List<TaskResponseDto>> rm = new ResponseManager<>();
        User user = userRepository.findUserByUuid(userUuid);
        if (user != null) {
            List<Task> tasks = taskRepository.viewUserDoneTasks(userUuid);
            if (tasks.size() > 0) {
                List<TaskResponseDto> responseDtoList = new ArrayList<>();
                tasks.forEach(task -> {
                    TaskResponseDto map = mapper.map(task, TaskResponseDto.class);
                    responseDtoList.add(map);
                });
                return rm.success(responseDtoList);
            }
            return responseManager.error("No task in DONE category");
        }
        return responseManager.error("You are not a user");
    }

    @Override
    public ApiResponse makeTaskPending(String userUuid, String title) {
        User user = userRepository.findUserByUuid(userUuid);
        if (user != null) {
            Task task = taskRepository.findTaskByTitle(title);
            if (Objects.nonNull(task)) {
                task.setUpdatedAt(new Date());
                int update = taskRepository.makeTaskPending(userUuid, title);
                TaskResponseDto responseDto = mapper.map(task, TaskResponseDto.class);
                return responseManager.success(responseDto);
            }
            return responseManager.error("Task " + title + " does not exist");
        }
        return responseManager.error("You are not a user");
    }

    @Override
    public ApiResponse makeTaskDone(String userUuid, String title) {
        User user = userRepository.findUserByUuid(userUuid);
        if (user != null) {
            Task task = taskRepository.findTaskByTitle(title);
            if (Objects.nonNull(task)) {
                task.setUpdatedAt(new Date());
                int update = taskRepository.makeTaskDone(userUuid, title);
                TaskResponseDto responseDto = mapper.map(task, TaskResponseDto.class);
                return responseManager.success(responseDto);
            }
            return responseManager.error("Task " + title + " does not exist");
        }
        return responseManager.error("You are not a user");
    }

    @Override
    public ApiResponse makeTaskInProgress(String userUuid, String title) {
        User user = userRepository.findUserByUuid(userUuid);
        if (user != null) {
            Task task = taskRepository.findTaskByTitle(title);
            if (Objects.nonNull(task)) {
                task.setUpdatedAt(new Date());
                int update = taskRepository.makeTaskInProgress(userUuid, title);
                TaskResponseDto responseDto = mapper.map(task, TaskResponseDto.class);
                return responseManager.success(responseDto);
            }
            return responseManager.error("Task " + title + " does not exist");
        }
        return responseManager.error("You are not a user");
    }
}
