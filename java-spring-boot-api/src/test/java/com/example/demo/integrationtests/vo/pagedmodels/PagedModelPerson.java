package com.example.demo.integrationtests.vo.pagedmodels;

import com.example.demo.integrationtests.vo.PersonVO;
import jakarta.xml.bind.annotation.XmlElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class PagedModelPerson implements Serializable {
    @Serial
    private static final long serialVersionUID = 3407598728459599712L;

    @XmlElement(name = "content")
    private List<PersonVO> content;

    public PagedModelPerson() {}

    public List<PersonVO> getContent() {
        return content;
    }

    public void setContent(List<PersonVO> content) {
        this.content = content;
    }
}
