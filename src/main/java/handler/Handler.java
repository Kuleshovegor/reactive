package handler;

import com.mongodb.rx.client.Success;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import model.Currency;
import model.Product;
import model.User;
import rx.Observable;
import service.Service;

public class Handler {
    private static final String OK = "OK";
    private static final String FAIL = "FAIL";

    private final Service service;

    public Handler(Service service) {
        this.service = service;
    }

    public Observable<String> handleRequest(HttpServerRequest<ByteBuf> request) {
        System.out.println(request.getDecodedPath());
        switch (request.getDecodedPath()) {
            case "/register":
                String login = request.getQueryParameters().get("login").get(0);
                Currency currency = Currency.valueOf(request.getQueryParameters().get("currency").get(0));

                return service.registerUser(login, currency)
                        .map(success -> success.equals(Success.SUCCESS) ? OK : FAIL);
            case "/add-product":
                String name = request.getQueryParameters().get("name").get(0);
                Currency currencyAdd = Currency.valueOf(request.getQueryParameters().get("currency").get(0));
                double price =  Double.parseDouble(request.getQueryParameters().get("price").get(0));

                return service.addProduct(name, price, currencyAdd)
                        .map(success -> success.equals(Success.SUCCESS) ? OK : FAIL);
            case "/get-products":
                long userId =  Long.parseLong(request.getQueryParameters().get("userId").get(0));

                return service.getProductByUser(userId).map(Product::toString);
            case "/get-user":
                long id =  Long.parseLong(request.getQueryParameters().get("userId").get(0));

                return service.getUser(id).map(User::toString);
            default:
                return Observable.just(FAIL);
        }
    }
}
