package com.labdessoft.roteiro01.dto;

import javax.validation.constraints.Size;

import com.labdessoft.roteiro01.entity.TaskPriorityEnum;
import com.labdessoft.roteiro01.entity.TaskTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOCreateTask {

    @NotBlank(message = "A descrição da tarefa é obrigatória")
    @Size(min = 10, message = "A descrição da tarefa deve possuir pelo menos 10 caracteres")
    private String description;

    @NotNull(message = "O tipo da tarefa é obrigatório, ou seja, não pode ser nulo")
    private TaskTypeEnum type;

    @NotNull(message = "A prioridade da tarefa é obrigatória, ou seja, não pode ser nula")
    private TaskPriorityEnum priority;

}