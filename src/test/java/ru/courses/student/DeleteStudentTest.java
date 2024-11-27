package ru.courses.student;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.courses.model.request.StudentRequest;

import java.util.List;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteStudentTest {
    private StudentApi studentApi;
    private StudentRequest firstStudentRequest;
    private int id;

    @BeforeEach
    public void setUp() {
        studentApi = new StudentApi();
        id = (int) (Math.random() * 100 + 1);
        firstStudentRequest = new StudentRequest(id, "Vadim", List.of(5, 4, 3, 5));
        studentApi.createStudent(firstStudentRequest);
    }

    @AfterEach
    public void down() {
        studentApi.deleteStudent(id);
    }

    @Test
    @DisplayName("Удаление существующего студента по id")
    public void deleteExistStudent() {
        Response response = studentApi.deleteStudent(id);
        assertEquals(SC_OK, response.statusCode());
    }

    @Test
    @DisplayName("Попытка удаления несуществующего студента по id")
    public void deleteNotExistStudent() {
        Response response = studentApi.deleteStudent(id+1);
        assertEquals(SC_NOT_FOUND, response.statusCode());
    }

}
