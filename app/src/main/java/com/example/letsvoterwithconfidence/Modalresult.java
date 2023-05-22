package com.example.letsvoterwithconfidence;

public class Modalresult {
    String name,partyname,votes,rank;

    public Modalresult() {
    }

    public Modalresult(String name, String partyname, String votes, String rank) {
        this.name = name;
        this.partyname = partyname;
        this.votes = votes;
        this.rank = rank;
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

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
