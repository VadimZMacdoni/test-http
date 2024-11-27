package ru.courses.student;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.courses.model.request.StudentRequest;
import ru.courses.model.response.StudentResponse;

import java.util.List;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetStudentTest {
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
    @DisplayName("Получение существующего студента по id")
    public void getExistStudent() {
        Response response = studentApi.getStudent(id);
        StudentResponse dsResponse = response.as(StudentResponse.class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(SC_OK).isEqualTo(response.statusCode());
        softly.assertThat(id).isEqualTo(dsResponse.getId());
        softly.assertAll();
    }

    @Test
    @DisplayName("Попытка получения несуществующего студента по id")
    public void getNotExistStudent() {
        Response response = studentApi.getStudent(id+1);
        assertEquals(SC_NOT_FOUND, response.statusCode());
    }
}
