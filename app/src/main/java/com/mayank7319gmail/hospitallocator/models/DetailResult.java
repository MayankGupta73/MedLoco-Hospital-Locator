package com.mayank7319gmail.hospitallocator.models;

/**
 * Created by Mayank Gupta on 23-12-2017.
 */

public class DetailResult {
    String status;
    DetailSinglePlace result;

    public DetailResult() {
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DetailSinglePlace getResult() {
        return result;
    }

    public void setResult(DetailSinglePlace result) {
        this.result = result;
    }
}
