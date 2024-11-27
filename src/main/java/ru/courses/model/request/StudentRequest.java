package ru.courses.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
@Data
@AllArgsConstructor
@Getter
public class StudentRequest {
        private Integer id;
        private String name;
        private List<Integer> marks;
}
