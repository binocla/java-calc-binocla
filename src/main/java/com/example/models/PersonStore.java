package com.example.models;

import io.smallrye.mutiny.Uni;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class PersonStore {
    private CopyOnWriteArrayList<Person> people;

    public Uni<List<Person>> getPeople() {
        return Uni.createFrom().item(Collections.unmodifiableList(people));
    }

    @PostConstruct
    void initPeople() {
        people = new CopyOnWriteArrayList<>(List.of(new Person("Default", "Default")));
    }

    public Uni<Person> getPerson(String personName) {
        return Uni.createFrom()
                .item(people
                        .stream()
                        .parallel()
                        .filter(x -> personName.equals(x.getFirstName()))
                        .findAny()
                        .orElse(null));
    }

    public void addPerson(Person p) {
        people.remove(new Person("Default", "Default"));
        people.add(p);
    }
}
