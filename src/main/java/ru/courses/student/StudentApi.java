package ru.courses.student;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.courses.model.request.StudentRequest;

public class StudentApi extends BaseApiClient {
    public Response createStudent(StudentRequest student) {
        return getPostSpec()
                .body(student)
                .post("/student");
    }

    public Response deleteStudent(int id) {
        return getPostSpec()
                .delete("/student/" + id);
    }

    public Response getStudent(int id) {
        return getPostSpec()
                .get("/student/" + id);
    }

    public Response getTopStudent() {
        return getPostSpec()
                .get("/topStudent/");
    }
}
