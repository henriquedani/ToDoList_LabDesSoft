package com.labdessoft.roteiro01.dto;

import com.labdessoft.roteiro01.entity.TaskPriorityEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOCreatePrazoTask {
    
    @NotBlank(message = "A descrição da tarefa é obrigatória")
    @Size(min = 10, message = "A descrição da tarefa deve possuir pelo menos 10 caracteres")
    private String description;
    
    @NotNull(message = "A prioridade da tarefa é obrigatória, ou seja, não pode ser nula")
    private TaskPriorityEnum priority;
    
    @Positive(message = "O prazo previsto de conclusão deve ser um número positivo")
    private Integer plannedDays;
}
