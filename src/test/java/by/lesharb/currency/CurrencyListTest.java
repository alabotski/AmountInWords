package by.lesharb.currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.base.VerifyException;
import org.junit.jupiter.api.Test;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-14.
 */
class CurrencyListTest {

    @Test
    void getCurrencyByCode() {
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
        Currency currency = CurrencyList.getCurrencyByName("UAH");
        assertNotNull(currency);
        assertEquals(currency.getCode(), 980);
    }
}