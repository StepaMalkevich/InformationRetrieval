package com.malkevich.server.model;

/**
 * Created by Stepan on 28.10.16.
 */
public class Item implements Comparable<Item> {
    private String film_id;
    private double true_score;
    private double pred_score;

    public Item(String film_id, double true_score, double pred_score) {
        this.film_id = film_id;
        this.true_score = true_score;
        this.pred_score = pred_score;
    }

    public String getFilm_id() {
        return film_id;
    }

    public void setFilm_id(String film_id) {
        this.film_id = film_id;
    }

    public double getTrue_score() {
        return true_score;
    }

    public void setTrue_score(double true_score) {
        this.true_score = true_score;
    }

    public double getPred_score() {
        return pred_score;
    }

    public void setPred_score(double pred_score) {
        this.pred_score = pred_score;
    }

    @Override
    public int compareTo(Item i) {
        if (this.pred_score < i.pred_score){
            return 1;
        } else {
            if (this.pred_score > i.pred_score) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
