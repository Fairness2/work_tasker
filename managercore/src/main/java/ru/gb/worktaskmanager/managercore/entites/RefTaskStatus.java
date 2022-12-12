package ru.gb.worktaskmanager.managercore.entites;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * Статусы задания
 */
@Data
@Entity
@Table(name = "ref_task_status")
@NoArgsConstructor
public class RefTaskStatus {
    /**
     * Стандартный формат даты
     */
    private static final String dateFormat = "yyyy-MM-dd kk:mm";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne()
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "status_code", nullable = false)
    private TaskStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    /**
     * Преобразователь даты в строку
     * @param date
     * @return String
     */
    private String dateToStr(LocalDateTime date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(RefTaskStatus.dateFormat);
        return date != null ? dateFormat.format(date) : null;
    }

    /**
     * Получим строковое представление даты создания
     * @return String
     */
    public String getStrCreatedAt() {
        return dateToStr(this.createdAt);
    }

    /**
     * Получим строковое представление даты создания
     * @return String
     */
    public String getStrEndedAt() {
        return dateToStr(this.endedAt);
    }
}