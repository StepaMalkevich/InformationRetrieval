package com.malkevich.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stepan on 21.10.16.
 */
public class User {
    private String user_id;
    private List<Item> items;

    public User(String user_id) {
        this.user_id = user_id;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<String> getListOfItems() {
        List<String> list = new ArrayList<>();

        for (Item item : this.items) {
            String film_id = item.getFilm_id();
            double true_score = item.getTrue_score();
            double pred_score = item.getPred_score();

            String elem = film_id + " : " + true_score + " : " + pred_score;

            list.add(elem);
        }

        return list;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
