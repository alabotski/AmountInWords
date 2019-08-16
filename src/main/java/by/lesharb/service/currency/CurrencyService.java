package by.lesharb.service.currency;

import by.lesharb.dto.Currency;
import io.vavr.Tuple3;
import java.util.List;
import java.util.Map;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-15.
 */
public interface CurrencyService {

    Currency getCurrencyByCode(int code);

    Currency getCurrencyByCode(String code);

    Currency getCurrencyByName(String name);

    void addCurrency(Currency currency);

    void addCurrency(String currency);

    void removeCurrency(Currency currency);

    void removeCurrency(String currency);

    List<Tuple3<Integer, String, Currency>> getCurrencies();

    Map<String, String> format(long amount);

    String format(long amount, Currency currency);
}
