package by.lesharb.currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.base.VerifyException;
import com.google.gson.Gson;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-14.
 */
@Slf4j
class CurrencyListTest {

    private static Gson gson;

    private static Currency eurCurrency;
    private static Currency rubCurrency;
    private static Currency uahCurrency;
    private static Currency usdCurrency;

    @BeforeAll
    public static void initTests() throws IOException {
        gson = new Gson();
        ClassPathResourceLoader loader = new ResourceResolver().getLoader(ClassPathResourceLoader.class).get();

        Optional<URL> eurJson = loader.getResource("classpath:currency/EUR.json");
        Reader eurReader = new InputStreamReader(eurJson.get().openStream(), StandardCharsets.UTF_8.name());
        eurCurrency = gson.fromJson(eurReader, Currency.class);

        Optional<URL> rubJson = loader.getResource("classpath:currency/RUB.json");
        Reader rubReader = new InputStreamReader(rubJson.get().openStream(), StandardCharsets.UTF_8.name());
        rubCurrency = gson.fromJson(rubReader, Currency.class);

        Optional<URL> uahJson = loader.getResource("classpath:currency/UAH.json");
        Reader uahReader = new InputStreamReader(uahJson.get().openStream(), StandardCharsets.UTF_8.name());
        uahCurrency = gson.fromJson(uahReader, Currency.class);

        Optional<URL> usdJson = loader.getResource("classpath:currency/USD.json");
        Reader usdReader = new InputStreamReader(usdJson.get().openStream(), StandardCharsets.UTF_8.name());
        usdCurrency = gson.fromJson(usdReader, Currency.class);
    }

    @BeforeEach
    public void init() {
        CurrencyList.removeCurrency(eurCurrency);
        CurrencyList.removeCurrency(rubCurrency);
        CurrencyList.removeCurrency(uahCurrency);
        CurrencyList.removeCurrency(usdCurrency);
    }

    @Test
    void getCurrencyByCode() {
        CurrencyList.addCurrency(uahCurrency);

        Currency currency = CurrencyList.getCurrencyByCode(980);
        assertNotNull(currency);
        assertEquals(currency.getName(), "UAH");

        currency = CurrencyList.getCurrencyByCode("980");
        assertNotNull(currency);
        assertEquals(currency.getName(), "UAH");

        VerifyException thrown = assertThrows(VerifyException.class, () -> CurrencyList.getCurrencyByCode("test"));
        assertTrue(thrown.getMessage().equals("The code must be a number"));
    }

    @Test
    void getCurrencyByName() {
        CurrencyList.addCurrency(uahCurrency);

        Currency currency = CurrencyList.getCurrencyByName("UAH");
        assertNotNull(currency);
        assertEquals(currency.getCode(), 980);
    }

    @Test
    void addCurrency() {
        assertEquals(CurrencyList.getCurrencies().size(), 0);
        CurrencyList.addCurrency(uahCurrency);
        assertEquals(CurrencyList.getCurrencies().size(), 1);

        NullPointerException nullPointerException = assertThrows(NullPointerException.class,
                () -> CurrencyList.addCurrency(null));
        assertTrue(nullPointerException.getMessage().equals("Currency is null"));

        nullPointerException = assertThrows(NullPointerException.class,
                () -> CurrencyList.addCurrency(Currency.builder().build()));
        assertTrue(nullPointerException.getMessage().equals("Currency Currency(code=null, name=null, oneInteger=null, twoIntegers=null, fiveIntegers=null, integerSex=null, oneFraction=null, twoFractions=null, fiveFractions=null, fractionSex=null) is not properly initialized"));
    }
}