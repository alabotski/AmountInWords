package by.lesharb.currency;

import com.google.common.base.Verify;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Collection which holds all registered currencies.
 *
 * @author Aliaksei Labotski.
 * @since 2019-08-14.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyList {

    private static final List<Currency> currencies = new CopyOnWriteArrayList<>();

    public static Currency getCurrencyByCode(int code) {
        return currencies.stream()
                .filter(currency -> currency.getCode() == code)
                .findFirst()
                .orElse(Currency.builder()
                        .build());
    }

    public static Currency getCurrencyByCode(String code) {
        Verify.verify(NumberUtils.isCreatable(code), "The code must be a number");
        return getCurrencyByCode(Integer.parseInt(code));
    }

    public static Currency getCurrencyByName(String name) {
        return currencies.stream()
                .filter(currency -> currency.getName().equals(name))
                .findFirst()
                .orElse(Currency.builder()
                        .build());
    }

    /**
     * Register new currency within AmountInWords class. Method performes primitive validation in order to prevent
     * common errors in future.
     *
     * @param currency currency to be registered
     * @throws NullPointerException in case any field in Currency is null
     */
    public static void addCurrency(Currency currency) {
        if (currency == null) {
            throw new NullPointerException("Currency is null");
        }
        if (!CurrencyUtil.validate(currency)) {
            throw new NullPointerException("Currency " + currency + " is not properly initialized");
        }
        if (!isExists(currency)) {
            currencies.add(CurrencyUtil.clone(currency));
        }
    }

    public static boolean isExists(Currency currency) {
        Predicate<Currency> currencyFilterCode = currencyFilter -> currencyFilter.getCode() == currency.getCode();
        Predicate<Currency> currencyFilterName = currencyFilter -> currencyFilter.getName().equals(currency.getName());
        return currencies.stream().anyMatch(currencyFilterCode.and(currencyFilterName));
    }

    public static void removeCurrency(Currency currency) {
        currencies.remove(currency);
    }

    public static List<Currency> getCurrencies() {
        return new ArrayList<>(currencies);
    }
}
