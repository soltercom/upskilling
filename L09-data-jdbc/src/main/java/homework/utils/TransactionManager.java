package homework.utils;

public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);

}
