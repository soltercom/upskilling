package homework.model;

public interface Savepoint<T> {

    T save();

    void restore(T state);

}
