package com.example.resources;

import com.example.models.Person;
import com.example.models.PersonStore;
import io.quarkus.logging.Log;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Produces(MediaType.TEXT_HTML)
@Path("/person")
public class PersonResource {
    @Inject
    PersonStore personStore;

    @Location("index.html")
    Template indexTemplate;

    @Location("person.html")
    Template personTemplate;

    @SneakyThrows
    @GET
    public TemplateInstance people() {
        List<Person> people = personStore.getPeople().subscribeAsCompletionStage().get();
        return indexTemplate.data("people", people);
    }

    @SneakyThrows
    @GET
    @Path("{person}")
    public TemplateInstance person(@PathParam("person") String person) {
        Log.debug("GOTCHA " + personStore.getPeople().subscribeAsCompletionStage().get());
        Person p = personStore.getPerson(person).subscribeAsCompletionStage().get();
        if (p == null) {
            throw new NotFoundException();
        }
        return personTemplate.data("person", p);
    }

    @GET
    @Path("/add")
    // fixme Replace firstName with UUID or smthing (due to firstName collision)
    public String addPerson(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName) {
        if (firstName == null || lastName == null) {
            throw new NotFoundException();
        }
        Person p = new Person(firstName, lastName);
        personStore.addPerson(p);
        return p.toString();
    }
}
