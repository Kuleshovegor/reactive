package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import model.User;
import rx.Observable;

public class UserDao {
    private final MongoCollection<User> collection;

    public UserDao(MongoCollection<User> collection) {
        this.collection = collection;
    }

    public Observable<User> getUser(long userId)  {
        return collection
                .find(Filters.eq("id", userId))
                .toObservable();
    }

    public Observable<Success> addUser(User user)  {
        return collection.insertOne(user);
    }
}
