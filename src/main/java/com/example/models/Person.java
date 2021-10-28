package com.example.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
public class Person {
    private final String firstName;
    private final String lastName;
    private final LocalDateTime dateOfCreation;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfCreation = LocalDateTime.now();
    }
}
