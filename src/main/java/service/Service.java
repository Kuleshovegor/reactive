package service;

import com.mongodb.rx.client.Success;
import dao.ProductDao;
import dao.UserDao;
import model.Currency;
import model.Product;
import model.User;
import rx.Observable;

public class Service {
    private static final Currency DEFAULT_CURRENCY = Currency.USD;

    private final ProductDao productDao;
    private final UserDao userDao;
    private final CurrencyService currencyService;
    private long lastUserId = 1;
    private long lastProductId = 1;

    public Service(ProductDao productDao, UserDao userDao, CurrencyService currencyService) {
        this.productDao = productDao;
        this.userDao = userDao;
        this.currencyService = currencyService;
    }

    public Observable<Success> registerUser(String login, Currency currency) {
        return userDao.addUser(new User(lastUserId++, login, currency));
    }

    public Observable<Success> addProduct(String name, double price, Currency currency) {
        double newPrice = currencyService.transfer(price, currency, DEFAULT_CURRENCY);

        return productDao.addProduct(new Product(lastProductId++, name, newPrice));
    }

    public Observable<User> getUser(long userId) {
        return userDao.getUser(userId);
    }

    public Observable<Product> getProductByUser(long userId) {
        return Observable.combineLatest(
                productDao.getProducts(),
                userDao.getUser(userId),
                (product, user) -> new Product(
                        product.getId(),
                        product.getName(),
                        currencyService.transfer(product.getPrice(), DEFAULT_CURRENCY, user.getCurrency())
                )
        );
    }
}
