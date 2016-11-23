package models;

import java.io.Serializable;

public class Bank implements Serializable{
    String bankName, type;

    public Bank( String bankName, String type ) {
        this.bankName = bankName;
        this.type = type;
    }

    public Bank() {
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName( String bankName ) {
        this.bankName = bankName;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }
}
