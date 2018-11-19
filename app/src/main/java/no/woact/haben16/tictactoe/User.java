package no.woact.haben16.tictactoe;

public class User {

    private String name;
    private int wins;
    private int _id;

    public User(String name){
        this.name = name;
    }

    public User(String name, int wins) {
        this.name = name;
        this.wins = wins;
    }

    public int getId() { return _id; }
    public void setId(int id) { this._id = _id; }
    public int getWins() { return wins; }
    public void setWins(int wins) { wins = wins; }
    public String getName() { return name;}
    public void setName(String name) { this.name = name; }
}
