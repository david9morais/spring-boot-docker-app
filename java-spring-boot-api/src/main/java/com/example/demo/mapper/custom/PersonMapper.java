package com.example.demo.mapper.custom;

import com.example.demo.model.Person;
import com.example.demo.valueobject.v2.PersonVOV2;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PersonMapper {

    public PersonVOV2 convertEntityToVo(Person person) {
        PersonVOV2 vo = new PersonVOV2();
        vo.setId(person.getId());
        vo.setFirstName(person.getFirstName());
        vo.setLastName(person.getLastName());
        vo.setGender(person.getGender());
        vo.setAddress(person.getAddress());
        vo.setBirthDay(new Date());

        return vo;
    }

    public Person convertVoTOEntity(PersonVOV2 person) {
        Person entity = new Person();
        entity.setId(person.getId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());
        entity.setAddress(person.getAddress());
        //entity.setBirthDay(new Date());

        return entity;
    }
}
