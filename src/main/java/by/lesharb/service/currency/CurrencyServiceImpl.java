package by.lesharb.service.currency;

import by.lesharb.dto.Currency;
import com.google.common.base.Verify;
import com.google.gson.Gson;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import io.vavr.Tuple3;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Service for work with collection which holds all registered currencies.
 *
 * @author Aliaksei Labotski.
 * @since 2019-08-15.
 */
@Slf4j
@Singleton
public class CurrencyServiceImpl implements CurrencyService {

    private static final List<Tuple3<Integer, String, Currency>> currencies = new ArrayList<>();
    private static final Gson gson = new Gson();
    private static final ClassPathResourceLoader loader = new ResourceResolver()
            .getLoader(ClassPathResourceLoader.class).get();

    @Override
    public Currency getCurrencyByCode(int code) {
        return currencies.stream()
                .filter(currency -> currency._1 == code)
                .findAny()
                .orElse(new Tuple3<>(0, StringUtils.EMPTY, Currency.builder()
                        .build()))
                ._3;
    }

    @Override
    public Currency getCurrencyByCode(String code) {
        Verify.verify(NumberUtils.isCreatable(code), "The code must be a number");
        return getCurrencyByCode(Integer.parseInt(code));
    }

    @Override
    public Currency getCurrencyByName(String name) {
        return currencies.stream()
                .filter(currency -> currency._2.equals(name))
                .findAny()
                .orElse(new Tuple3<>(0, StringUtils.EMPTY, Currency.builder()
                        .build()))
                ._3;
    }

    private CurrencyEnum getCurrency(String currency) {
        CurrencyEnum currencyEnum = CurrencyEnum.valueOf(currency);
        if (currencyEnum == null) {
            String errMsg = "Current currency is not supported";
            log.error(errMsg);
            throw new IllegalArgumentException(errMsg);
        } else {
            return currencyEnum;
        }
    }

    /**
     * Register new currency within AmountInWords class. Method performes primitive validation in order to prevent
     * common errors in future.
     *
     * @param currency currency to be registered
     * @throws NullPointerException in case any field in Currency is null
     */
    @Override
    public void addCurrency(Currency currency) {
        if (currency == null) {
            throw new NullPointerException("Currency is null");
        }
        if (!validate(currency)) {
            throw new NullPointerException("Currency " + currency + " is not properly initialized");
        }
        if (!isExists(currency)) {
            Currency currencyClone = clone(currency);
            Tuple3<Integer, String, Currency> currencyTuple = new Tuple3<>(currencyClone.getCode(),
                    currencyClone.getName(), currencyClone);
            currencies.add(currencyTuple);
        }
    }

    @Override
    public void addCurrency(String currency) {
        CurrencyEnum currencyEnum = getCurrency(currency);
        Currency currencyRead = readCurrency(currencyEnum);
        addCurrency(currencyRead);
    }

    public static boolean isExists(Currency currency) {
        Predicate<Tuple3<Integer, String, Currency>> currencyFilterCode = tupleCurrency -> tupleCurrency._1 == currency
                .getCode();
        Predicate<Tuple3<Integer, String, Currency>> currencyFilterName = tupleCurrency -> tupleCurrency._2
                .equals(currency.getName());
        return currencies.stream().anyMatch(currencyFilterCode.and(currencyFilterName));
    }

    @Override
    public void removeCurrency(Currency currency) {
        Tuple3<Integer, String, Currency> currencyTuple = new Tuple3<>(currency.getCode(), currency.getName(),
                currency);
        currencies.remove(currencyTuple);
    }

    @Override
    public void removeCurrency(String currency) {
        Currency currencyFind = getCurrencyByName(currency);
        removeCurrency(currencyFind);
    }

    @Override
    public List<Tuple3<Integer, String, Currency>> getCurrencies() {
        return new ArrayList<>(currencies);
    }

    /**
     * Format amount in words using all currencies.
     *
     * @param amount amount to be formatted
     * @return {@link List} of amount in word
     * @see CurrencyService#format(long, Currency)
     */
    @Override
    public Map<String, String> format(long amount) {
        return currencies.stream().collect(
                Collectors.toMap(tupleCurrency -> tupleCurrency._2, tupleCurrency -> format(amount, tupleCurrency._3)));
    }

    @Override
    public String format(long amount, Currency currency) {
        return null;
    }

    private boolean validate(Currency currency) {
        return !Stream.of(currency.getCode(),
                currency.getName(),
                currency.getOneInteger(),
                currency.getTwoIntegers(),
                currency.getFiveIntegers(),
                currency.getIntegerSex(),
                currency.getOneFraction(),
                currency.getTwoFractions(),
                currency.getFiveFractions(),
                currency.getFractionSex())
                .allMatch(Objects::isNull);
    }

    private Currency clone(Currency currency) {
        return Currency.builder()
                .code(currency.getCode())
                .name(currency.getName())
                .oneInteger(currency.getOneInteger())
                .twoIntegers(currency.getTwoIntegers())
                .fiveIntegers(currency.getFiveIntegers())
                .integerSex(currency.getIntegerSex())
                .oneFraction(currency.getOneFraction())
                .twoFractions(currency.getTwoFractions())
                .fiveFractions(currency.getFiveFractions())
                .fractionSex(currency.getFractionSex())
                .build();
    }

    private Currency readCurrency(CurrencyEnum currencyEnum) {
        try {
            Optional<InputStream> optionalInputStream = loader
                    .getResourceAsStream("classpath:currency/" + currencyEnum.name() + ".json");
            InputStream is = optionalInputStream
                    .orElseThrow(() -> new RuntimeException("No settings for this currency"));
            Reader uahReader = new InputStreamReader(is, StandardCharsets.UTF_8.name());
            return gson.fromJson(uahReader, Currency.class);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            return Currency.builder().build();
        }
    }
}
