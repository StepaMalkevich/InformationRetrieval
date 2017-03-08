package com.malkevich.server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created by Stepan on 21.10.16.
 */
@Path("/greet")
public class GreetApi {
    @Path("/{user_id}")
    @GET
    public String calculateExpression(@PathParam("user_id") final String user_id) {
        return "Greeting " + user_id;
    }

}
