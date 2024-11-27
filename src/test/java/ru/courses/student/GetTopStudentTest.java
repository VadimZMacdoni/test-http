package ru.courses.student;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.courses.model.request.StudentRequest;
import ru.courses.model.response.StudentResponse;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetTopStudentTest {
    private StudentApi studentApi;
    private StudentRequest firstStudentRequest;
    private StudentRequest secondStudentRequest;
    private StudentRequest thirdStudentRequest;
    private StudentRequest fourthStudentRequest;
    private int[] ids = new int[5];

    @BeforeEach
    public void setUp() {
        studentApi = new StudentApi();
        for(int i=0; i<ids.length; i++) {
            ids[i] = (int) (Math.random() * 100 + 1);
        }
        firstStudentRequest = new StudentRequest(ids[0], "Vadim", null);
        secondStudentRequest = new StudentRequest(ids[1], "Vera", List.of(5, 4));
        thirdStudentRequest = new StudentRequest(ids[2], "Lena", List.of(5, 4, 5, 4));
        fourthStudentRequest = new StudentRequest(ids[3], "Anton", List.of(5, 4, 5, 4));
    }

    @Test
    @DisplayName("Проверка рейтинга, когда в БД нет ни одного студента")
    public void getTopStudentTest1() {
        Response response = studentApi.getTopStudent();
        assertEquals(SC_OK, response.statusCode());
        assertTrue(response.asString().isEmpty());
    }

    @Test
    @DisplayName("Проверка рейтинга, когда в БД нет ни одного студента")
    public void getTopStudentTest2() {
        studentApi.createStudent(firstStudentRequest);
        Response response = studentApi.getTopStudent();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(SC_OK).isEqualTo(response.statusCode());
        softly.assertThat(response.asString().isEmpty()).isEqualTo(true);
        softly.assertAll();
        studentApi.deleteStudent(ids[0]);
    }

    @Test
    @DisplayName("Получение студента с максимальной средней оценкой, когда кол-во таких студентов = 1")
    public void getTopStudentTest3() {
        studentApi.createStudent(secondStudentRequest);
        Response response = studentApi.getTopStudent();
        StudentResponse[] dsResponse = response.as(StudentResponse[].class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(secondStudentRequest.getId()).isEqualTo(dsResponse[0].getId());
        softly.assertThat(secondStudentRequest.getName()).isEqualTo(dsResponse[0].getName());
        softly.assertThat(secondStudentRequest.getMarks()).isEqualTo(dsResponse[0].getMarks());
        softly.assertThat(SC_OK).isEqualTo(response.statusCode());
        softly.assertAll();
        studentApi.deleteStudent(ids[1]);
    }

    @Test
    @DisplayName("Получение студента с максимальной средней оценкой, когда кол-во таких студентов > 1 при этом у них разное кол-во оценок")
    public void getTopStudentTest4() {
        studentApi.createStudent(secondStudentRequest);
        studentApi.createStudent(thirdStudentRequest);
        Response response = studentApi.getTopStudent();
        StudentResponse[] dsResponse = response.as(StudentResponse[].class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(secondStudentRequest.getId()).isEqualTo(dsResponse[0].getId());
        softly.assertThat(secondStudentRequest.getName()).isEqualTo(dsResponse[0].getName());
        softly.assertThat(secondStudentRequest.getMarks()).isEqualTo(dsResponse[0].getMarks());
        softly.assertThat(SC_OK).isEqualTo(response.statusCode());
        softly.assertAll();
        studentApi.deleteStudent(ids[1]);
        studentApi.deleteStudent(ids[2]);
    }

    @Test
    @DisplayName("Получение студентов с максимальной средней оценкой, когда кол-во таких студентов > 1 при этом у них одинаковое кол-во оценок")
    public void getTopStudentTest5() {
        studentApi.createStudent(secondStudentRequest);
        studentApi.createStudent(thirdStudentRequest);
        studentApi.createStudent(fourthStudentRequest);
        Response response = studentApi.getTopStudent();
        StudentResponse[] dsResponse = response.as(StudentResponse[].class);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(secondStudentRequest.getId()).isEqualTo(dsResponse[0].getId());
        softly.assertThat(secondStudentRequest.getName()).isEqualTo(dsResponse[0].getName());
        softly.assertThat(secondStudentRequest.getMarks()).isEqualTo(dsResponse[0].getMarks());
        softly.assertThat(secondStudentRequest.getId()).isEqualTo(dsResponse[1].getId());
        softly.assertThat(secondStudentRequest.getName()).isEqualTo(dsResponse[1].getName());
        softly.assertThat(secondStudentRequest.getMarks()).isEqualTo(dsResponse[1].getMarks());
        softly.assertThat(SC_OK).isEqualTo(response.statusCode());
        softly.assertAll();
        studentApi.deleteStudent(ids[1]);
        studentApi.deleteStudent(ids[2]);
        studentApi.deleteStudent(ids[3]);
    }

}
