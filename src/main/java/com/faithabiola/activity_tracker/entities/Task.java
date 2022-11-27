package com.faithabiola.activity_tracker.entities;

import com.faithabiola.activity_tracker.utils.Uuid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "Task")
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            nullable = false
    )
    private Long id;
    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String title;
    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;
    @Column(
            name = "status",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String status;
    @Column(
            name = "createdAt",
            nullable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private Date createdAt;
    @Column(
            name = "updatedAt",
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private Date updatedAt;
    @Column(
            name = "completedAt",
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private Date completedAt;
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "task_user_id_fk"
            )
    )
    private User user;
    @Column(
            name = "uuid",
            nullable = false
    )
    private String uuid;

    @PrePersist
    public void perPersist() {
        this.createdAt = new Date();
        this.status = "Pending";
        this.uuid = Uuid.uuid();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", completedAt=" + completedAt +
                ", user=" + user +
                '}';
    }
}
