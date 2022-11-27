package com.faithabiola.activity_tracker.repositories;

import com.faithabiola.activity_tracker.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByTitle(String title);

    Task findTaskByTitle(String title);

    List<Task> findAllTasksByUserUuid(String userUuid);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM task WHERE title = ?1",
            nativeQuery = true
    )
    int deleteTaskByTitle(String title);

    @Query(
            value = "SELECT task.* FROM task JOIN users u on u.id = task.user_id WHERE u.uuid = ?1 AND status = 'InProgress'",
            nativeQuery = true
    )
    List<Task> viewUserInProgressTasks(String userUuid);
    @Query(
            value = "SELECT task.* FROM task JOIN users u on u.id = task.user_id WHERE u.uuid = ?1 AND status = 'Pending'",
            nativeQuery = true
    )
    List<Task> viewUserPendingTasks(String userUuid);

    @Query(
            value = "SELECT task.* FROM task JOIN users u on u.id = task.user_id WHERE u.uuid = ?1 AND status = 'Done'",
            nativeQuery = true
    )
    List<Task> viewUserDoneTasks(String userUuid);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE task SET status = 1, completed_at = null FROM task t JOIN users u on u.id = t.user_id WHERE u.uuid = ?1 AND t.title = ?2",
            nativeQuery = true
    )
    int makeTaskPending(String userUuid, String title);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE task SET status = 2 FROM task t JOIN users u on u.id = t.user_id WHERE u.uuid = ?1 AND t.title = ?2",
            nativeQuery = true
    )
    int makeTaskDone(String userUuid, String title);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE task SET status = 0, completed_at = null FROM task t JOIN users u on u.id = t.user_id WHERE u.uuid = ?1 AND t.title = ?2",
            nativeQuery = true
    )
    int makeTaskInProgress(String userUuid, String title);
}
