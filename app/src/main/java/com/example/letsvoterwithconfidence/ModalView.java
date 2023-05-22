package com.example.letsvoterwithconfidence;

public class ModalView {
    String name,party,email;

    public ModalView() {
    }

    public ModalView(String name, String party, String email) {
        this.name = name;
        this.party = party;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
