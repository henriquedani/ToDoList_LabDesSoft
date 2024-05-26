package com.labdessoft.roteiro01.integration;

import com.labdessoft.roteiro01.Roteiro01Application;
import com.labdessoft.roteiro01.dto.DTOCreateDataTask;
import com.labdessoft.roteiro01.dto.DTOCreatePrazoTask;
import com.labdessoft.roteiro01.dto.DTOCreateTask;
import com.labdessoft.roteiro01.entity.TaskPriorityEnum;
import com.labdessoft.roteiro01.entity.TaskTypeEnum;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import org.springframework.http.HttpStatus;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.delete;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@SpringBootTest(classes = { Roteiro01Application.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class TaskControllerIntegrationTest {
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.port = 8080;
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
        get("/api/task").then().statusCode(200);
    }

    @Test
    public void givenUrl_whenSuccessOnGetsResponseAndJsonHasOneTask_thenCorrect() {
        given()
                .when()
                .get("/api/task/1")
                .then()
                .statusCode(200)
                .assertThat().body("description", equalTo("Primeira tarefa"));
    }

    @Test
    public void givenValidData_whenCreatingTask_thenCorrect() {
        DTOCreateTask createTaskDto = new DTOCreateTask();
        createTaskDto.setDescription("Tarefa de Teste");
        createTaskDto.setType(TaskTypeEnum.LIVRE);
        createTaskDto.setPriority(TaskPriorityEnum.ALTA);

        given()
                .contentType("application/json")
                .body(createTaskDto)
                .when()
                .post("/api/task/create/livre")
                .then()
                .statusCode(201);
    }

    @Test
    public void givenValidData_whenCreatingPrazoTask_thenCorrect() {
        DTOCreatePrazoTask createPrazoTaskDto = new DTOCreatePrazoTask();
        createPrazoTaskDto.setDescription("Tarefa com Prazo");
        createPrazoTaskDto.setPriority(TaskPriorityEnum.MEDIA);
        createPrazoTaskDto.setPlannedDays(5);

        given()
                .contentType("application/json")
                .body(createPrazoTaskDto)
                .when()
                .post("/api/task/create/prazo")
                .then()
                .statusCode(201);
    }

    @Test
    public void givenValidData_whenCreatingDataTask_thenCorrect() {
        DTOCreateDataTask createDataTaskDto = new DTOCreateDataTask();
        createDataTaskDto.setDescription("Tarefa com Data");
        createDataTaskDto.setPriority(TaskPriorityEnum.BAIXA);
        createDataTaskDto.setPlannedDate(LocalDate.now().plusDays(7));

        given()
                .contentType("application/json")
                .body(createDataTaskDto)
                .when()
                .post("/api/task/create/data")
                .then()
                .statusCode(201);
    }

    @Test
    public void givenValidTaskId_whenMarkingTaskAsCompleted_thenCorrect() {
        Long taskId = 1L;

        given()
                .pathParam("id", taskId)
                .when()
                .patch("/api/task/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void givenValidTaskId_whenDeletingTask_thenCorrect() {
        Long taskId = 1L;

        given()
                .pathParam("id", taskId)
                .when()
                .delete("/api/task/{id}")
                .then()
                .statusCode(204);
    }
}
