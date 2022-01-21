package homework.db;

public interface TransactionRunner {

    <T> T doInTransaction(TransactionAction<T> action);

}
