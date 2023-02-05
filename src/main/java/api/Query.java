package api;

public interface Query<V> {
    V apply();

    void printAns();
}
