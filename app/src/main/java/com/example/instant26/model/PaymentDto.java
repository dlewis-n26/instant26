package com.example.instant26.model;

public class PaymentDto {

    private String id;

    private String requestor;

    private String iban;

    private String message;

    private Double amount;

    public PaymentDto() {

    }

    public PaymentDto(String id, String requestor, String iban, String message, Double amount) {
        this.id = id;
        this.requestor = requestor;
        this.iban = iban;
        this.message = message;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}