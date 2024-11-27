package ru.courses.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
public class StudentResponse {
    private Integer id;
    private String name;
    private List<Integer> marks;
}
