package com.example.demo.repositories;

import com.example.demo.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying
    @Query("UPDATE Person p set p.enabled = false where p.id = :id")
    void disablePerson(@Param("id") Long id);

    @Query("SELECT p from Person p where p.firstName LIKE LOWER(CONCAT('%', :firstName, '%'))")
    Page<Person> findPeopleByName(@Param("firstName") String firstName, Pageable pageable);
}
