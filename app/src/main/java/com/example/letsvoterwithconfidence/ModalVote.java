package com.example.letsvoterwithconfidence;

public class ModalVote {
    String name,partyname,email,votes;


    public ModalVote() {
    }

    public ModalVote(String name, String partyname, String email, String votes) {
        this.name = name;
        this.partyname = partyname;
        this.email = email;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartyname() {
        return partyname;
    }

    public void setPartyname(String partyname) {
        this.partyname = partyname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }
}
