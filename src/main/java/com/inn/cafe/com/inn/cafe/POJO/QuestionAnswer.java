package com.inn.cafe.com.inn.cafe.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswer {

    private Integer questionId;
    private Integer answerId;
}
