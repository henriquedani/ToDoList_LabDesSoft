package com.labdessoft.roteiro01.service;

import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.entity.TaskPriorityEnum;
import com.labdessoft.roteiro01.dto.DTOCreateDataTask;
import com.labdessoft.roteiro01.dto.DTOCreatePrazoTask;
import com.labdessoft.roteiro01.dto.DTOCreateTask;
import com.labdessoft.roteiro01.entity.TaskTypeEnum;
import com.labdessoft.roteiro01.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.Operation;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    @Operation(summary = "Lista todas as tarefas da lista")
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Operation(summary = "Cria uma nova tarefa do tipo LIVRE, passando somente a descrição inserida da tarefa")
    public Task create(String descricao) {
        Task novaTask = new Task(descricao);
        return taskRepository.save(novaTask);
    }

    @Operation(summary = "Cria uma nova tarefa do tipo PRAZO, recebendo os dados do DTOCreatePrazoTask")
    public Task createPrazoTask(DTOCreatePrazoTask taskDto) {
        if (taskDto.getDescription() == null || taskDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("A descrição da tarefa é obrigatória");
        }

        Integer plannedDays = taskDto.getPlannedDays();
        if (plannedDays == null || plannedDays <= 0) {
            throw new IllegalArgumentException("O prazo previsto de conclusão deve ser um número positivo");
        }

        Task novaTask = new Task(taskDto.getDescription(), TaskTypeEnum.PRAZO, taskDto.getPriority(), plannedDays);

        return taskRepository.save(novaTask);
    }

    @Operation(summary = "Cria uma nova tarefa do tipo DATA, recebendo os dados do DTOCreateDataTask")
    public Task createDataTask(DTOCreateDataTask taskDto) {
        if (taskDto.getDescription() == null || taskDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("A descrição da tarefa é obrigatória");
        }

        LocalDate plannedDate = taskDto.getPlannedDate();
        if (plannedDate == null || plannedDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data prevista de conclusão deve ser no presente ou no futuro");
        }

        Task novaTask = new Task(taskDto.getDescription(), TaskTypeEnum.DATA, taskDto.getPriority(), plannedDate);

        return taskRepository.save(novaTask);
    }

    @Operation(summary = "Marca uma tarefa, cuja ID foi passada, como concluída")
    public Task marcarTarefaComoConcluida(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com o ID: " + id));
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    @Operation(summary = "Deleta a tarefa de acordo com o ID passado")
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
