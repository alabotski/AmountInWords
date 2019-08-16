package by.lesharb.service.currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.lesharb.dto.Currency;
import com.google.common.base.VerifyException;
import io.micronaut.test.annotation.MicronautTest;
import java.util.Map;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-15.
 */
@Slf4j
@MicronautTest
class CurrencyServiceTest {

    @Inject
    private CurrencyService currencyService;

    @BeforeEach
    public void initEach() {
        currencyService.removeCurrency("UAH");
    }

    @Test
    void getCurrencyByCode() {
        currencyService.addCurrency("UAH");
        Currency currency = currencyService.getCurrencyByCode(980);
        assertNotNull(currency);
        assertEquals(currency.getName(), "UAH");

        currency = currencyService.getCurrencyByCode("980");
        assertNotNull(currency);
        assertEquals(currency.getName(), "UAH");

        VerifyException thrown = assertThrows(VerifyException.class, () -> currencyService.getCurrencyByCode("test"));
        assertTrue(thrown.getMessage().equals("The code must be a number"));
    }

    @Test
    void getCurrencyByName() {
        currencyService.addCurrency("UAH");
        Currency currency = currencyService.getCurrencyByName("UAH");
        assertNotNull(currency);
        assertEquals(currency.getCode(), 980);

        currency = currencyService.getCurrencyByName("test");
        assertNotNull(currency);
        assertNull(currency.getCode());
        assertNull(currency.getName());
    }

    @Test
    void addCurrency() {
        int size = currencyService.getCurrencies().size();
        assertEquals(0, size);

        currencyService.addCurrency("UAH");
        size = currencyService.getCurrencies().size();
        assertEquals(1, size);

        Currency currency = Currency.builder()
                .code(1)
                .name("test")
                .build();
        currencyService.addCurrency(currency);
        size = currencyService.getCurrencies().size();
        assertEquals(2, size);
    }

    @Test
    void removeCurrency() {
        currencyService.addCurrency("UAH");
        int size = currencyService.getCurrencies().size();
        assertEquals(1, size);

        currencyService.removeCurrency("UAH");
        size = currencyService.getCurrencies().size();
        assertEquals(0, size);
    }

    @Test
    void format() {
        currencyService.addCurrency("UAH");
        currencyService.addCurrency("EUR");
        currencyService.addCurrency("USD");
        currencyService.addCurrency("RUB");

        Map<String, String> currencyMap = currencyService.format(999);
        assertEquals(4, currencyMap.size());
    }
}