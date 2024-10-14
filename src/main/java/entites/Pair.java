package entites;

public class Pair<First, Second> {
    public First first;
    public Second second;

    public Pair(First key, Second element) {
        this.second = element;
        this.first = key;
    }
}
