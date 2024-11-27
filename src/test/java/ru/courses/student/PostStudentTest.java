package ru.courses.student;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.courses.model.request.StudentRequest;
import ru.courses.model.response.StudentResponse;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostStudentTest {
    private StudentApi studentApi;
    private StudentRequest firstStudentRequest;
    private StudentRequest secondStudentRequest;
    private StudentRequest thirdStudentRequest;
    private StudentRequest fourthStudentRequest;
    private int id;

    @BeforeEach
    public void setUp() {
        studentApi = new StudentApi();
        id = (int) (Math.random() * 100 + 1);
        firstStudentRequest = new StudentRequest(id, "Vadim", List.of(5, 4, 3, 5));
        secondStudentRequest = new StudentRequest(id, "Vera", List.of(5, 4, 3, 5, 5, 5));
        thirdStudentRequest = new StudentRequest(null, "Lena", List.of(5, 5, 5));
        fourthStudentRequest = new StudentRequest(id, null, List.of(3, 4, 5));
    }

    @AfterEach
    public void down() {
        studentApi.deleteStudent(id);
    }

    @Test
    @DisplayName("Добавление студента")
    public void addStudentTest() {
        Response response = studentApi.createStudent(firstStudentRequest);
        assertEquals(SC_CREATED, response.statusCode());
    }

    @Test
    @DisplayName("Обновление студента")
    public void updateStudentTest() {
        Response firstResponse = studentApi.createStudent(firstStudentRequest);
        Response secondResponse = studentApi.createStudent(secondStudentRequest);
        assertEquals(SC_CREATED, secondResponse.statusCode());
    }

    @Test
    @DisplayName("Добавление студента с пустым id")
    public void addStudentWithNullIdTest() {
        Response response = studentApi.createStudent(thirdStudentRequest);
        assertEquals(SC_CREATED, response.statusCode());

    }

    @Test
    @DisplayName("Добавление студента с пустым именем")
    public void addStudentWithEmptyNameTest() {
        Response response = studentApi.createStudent(fourthStudentRequest);
        assertEquals(SC_BAD_REQUEST, response.statusCode());
    }
}
