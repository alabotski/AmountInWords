package by.lesharb.service.currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.lesharb.dto.Currency;
import com.google.common.base.VerifyException;
import io.micronaut.test.annotation.MicronautTest;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
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
        assertTrue(currencyService.getCurrencies().size() == 0);
    }

    @Test
    void addCurrency() {
    }

    @Test
    void removeCurrency() {
    }

    @Test
    void getCurrencies() {
    }
}