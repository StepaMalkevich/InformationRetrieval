package com.malkevich.server.rest;

import com.malkevich.server.model.Item;
import com.malkevich.server.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Stepan on 23.04.16.
 */
@Path("/recommend")
@Produces(MediaType.APPLICATION_JSON)
public class RecommendApi {
    @Path("/{user_id}")
    @GET
    public List<String> calculateExpression(@PathParam("user_id") final String user_id) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/Stepan/InformationRetrieval/test_result.txt"));
        String line;

        System.out.println("in calculateExpression");
        List<User> userList = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(line, "\t");
            String u_id = tokenizer.nextToken();

            User user = new User(u_id);
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                StringTokenizer tokenizer1 = new StringTokenizer(token, "@");
                String film_id = tokenizer1.nextToken();
                String true_score = tokenizer1.nextToken();
                String pred_score = tokenizer1.nextToken();

                Item item = new Item(film_id, Double.valueOf(true_score), Double.valueOf(pred_score));
                user.addItem(item);
            }

            userList.add(user);
        }

        for (User u : userList){
            if (u.getUser_id().equals(user_id)){
                return u.getListOfItems();
            }
        }

        List<String> lst = new ArrayList<>();
        lst.add("nothing");
        return lst;
    }

}
