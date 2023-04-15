import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoDatabase;
import dao.ProductDao;
import dao.UserDao;
import handler.Handler;
import io.reactivex.netty.protocol.http.server.HttpServer;
import model.Product;
import model.User;
import rx.Observable;
import service.CurrencyService;
import service.Service;

public class Main {
    public static void main(String[] args) {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = client.getDatabase("hw4");

        ProductDao productDao = new ProductDao(database.getCollection("products", Product.class));
        UserDao userDao = new UserDao(database.getCollection("users", User.class));
        CurrencyService currencyService = new CurrencyService();
        Service service = new Service(productDao, userDao, currencyService);
        Handler handler = new Handler(service);

        HttpServer
                .newServer(8080)
                .start((req, resp) -> {
                    Observable<String> response = handler.handleRequest(req);

                    return resp.writeString(response);
                }).awaitShutdown();
    }
}
