package com.example.demo.integrationtests.vo.wrappers;

import com.example.demo.integrationtests.vo.PersonVO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class PersonEmbeddedVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4599142021702340466L;

    @JsonProperty("personVOList")
    private List<PersonVO> people;

    public PersonEmbeddedVO() {}

    public List<PersonVO> getPeople() {
        return people;
    }

    public void setPeople(List<PersonVO> people) {
        this.people = people;
    }
}
