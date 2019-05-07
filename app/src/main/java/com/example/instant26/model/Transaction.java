package com.example.instant26.model;

public class Transaction {

    public String sender;

    public boolean shown;

    public Transaction() {

    }

    public Transaction(String sender, boolean shown) {
        this.sender = sender;
        this.shown = shown;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
