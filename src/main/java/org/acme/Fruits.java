package org.acme;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.reactive.mutiny.Mutiny;

import io.smallrye.mutiny.Uni;

@Path("/fruits")
@Transactional
public class Fruits {
    @Inject
    Mutiny.Session mutinySession;

    @GET
    public Uni<List<Fruit>> all() {
        return mutinySession
            .createNamedQuery("Fruits.findAll", Fruit.class).getResults().collectItems()
            .asList();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> create(Fruit fruit) {
        return mutinySession
            .persist(fruit)
            .chain(mutinySession::flush)
            .map(ignore -> Response.ok(fruit).status(201).build());
    }

    @GET
    @Path("count")
    public Uni<Long> count() {
        return mutinySession
            .createNamedQuery("Fruits.findAll", Fruit.class).getResults().collectItems()
            .with(Collectors.counting());
    }
}
