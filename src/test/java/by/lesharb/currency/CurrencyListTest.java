package by.lesharb.currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.base.VerifyException;
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

    private static Currency uahCurrency;

    @BeforeAll
    public static void initTests() {
        uahCurrency = CurrencyUtil.readCurrency(CurrencyEnum.UAH);
    }

    @BeforeEach
    public void init() {
        CurrencyList.removeCurrency(uahCurrency);
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
        assertTrue(nullPointerException.getMessage()
                .equals("Currency Currency(code=null, name=null, oneInteger=null, twoIntegers=null, fiveIntegers=null, integerSex=null, oneFraction=null, twoFractions=null, fiveFractions=null, fractionSex=null) is not properly initialized"));
    }

    @Test
    void isExists() {
        boolean exists = CurrencyList.isExists(uahCurrency);
        assertFalse(exists);
        CurrencyList.addCurrency(uahCurrency);
        exists = CurrencyList.isExists(uahCurrency);
        assertTrue(exists);
    }
}