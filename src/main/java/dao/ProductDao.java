package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import model.Product;
import rx.Observable;

public class ProductDao {
    private final MongoCollection<Product> collection;

    public ProductDao(MongoCollection<Product> collection) {
        this.collection = collection;
    }

    public Observable<Product> getProduct(long productId)  {
        return collection
                .find(Filters.eq("id", productId))
                .toObservable();
    }

    public Observable<Product> getProducts()  {
        return collection
                .find()
                .toObservable();
    }

    public Observable<Success> addProduct(Product product)  {
        return collection.insertOne(product);
    }
}