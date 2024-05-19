package com.labdessoft.roteiro01.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Todos os detalhes relativos à uma tarefa. ")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "A descrição da tarefa deve possuir pelo menos 10 caracteres")
    @Size(min = 10, message = "A descrição da tarefa deve possuir pelo menos 10 caracteres")
    private String description;
    private LocalDate creationDate;

    private TaskPriorityEnum priority;
    private TaskTypeEnum type;

    private Boolean completed;
    private LocalDate plannedDate;

    private Integer plannedDays;
    private LocalDate lastModifiedPrazoDate;

    //Construtor inicial da primeira entrega
    public Task(String description) {
        this.description = description;
        this.completed = false;
        this.type = TaskTypeEnum.LIVRE;
    }

    //Construtor inicial estendido para criar uma Tarefa do Tipo LIVRE
    public Task(String description, TaskTypeEnum type, TaskPriorityEnum priority) {
        this.description = description;
        this.completed = false;
        this.type = type;
        this.priority = priority;
        this.creationDate = LocalDate.now();
    }

    // Construtor para Tarefa do Tipo PRAZO
    public Task(String description, TaskTypeEnum type, TaskPriorityEnum priority, Integer plannedDays) {
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.plannedDays = plannedDays;
        this.completed = false;
        this.creationDate = LocalDate.now();
        this.lastModifiedPrazoDate = LocalDate.now();
    }

    // Construtor para Tarefa do Tipo DATA
    public Task(String description, TaskTypeEnum type, TaskPriorityEnum priority, LocalDate plannedDate) {
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.plannedDate = plannedDate;
        this.completed = false;
        this.creationDate = LocalDate.now();
    }

    @Override
    public String toString() {
        String status = calculateStatus();
        return String.format("Task [id=%d, description=%s, priority=%s, type=%s, status=%s]",
                id, description, priority, getTypeDescription(type), status);
    }
    
    private String getTypeDescription(TaskTypeEnum type) {
        switch (type) {
            case DATA:
                return "Data";
            case PRAZO:
                return "Prazo";
            default:
                return "Livre";
        }
    }
    
    private String calculateStatus() {
        if (completed) {
            return "Concluída";
        }
        LocalDate currentDate = LocalDate.now();
        long daysLate;
        switch (type) {
            case DATA:
                if (plannedDate != null) {
                    daysLate = ChronoUnit.DAYS.between(plannedDate, currentDate);
                    return plannedDate.isBefore(currentDate) ? daysLate + " dias de atraso" : "Prevista";
                }
                break;
            case PRAZO:
                if (plannedDays != null) {
                    LocalDate dueDate = creationDate.plusDays(plannedDays);
                    daysLate = ChronoUnit.DAYS.between(dueDate, currentDate);
                    return dueDate.isBefore(currentDate) ? daysLate + " dias de atraso" : "Prevista";
                }
                break;
            case LIVRE:
                return "Prevista";
        }
        return "Prevista";
    }

}
