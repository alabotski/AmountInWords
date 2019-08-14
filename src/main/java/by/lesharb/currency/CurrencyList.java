package by.lesharb.currency;

import static by.lesharb.messages.MessageConstants.FIVE_EUR_FRACTION;
import static by.lesharb.messages.MessageConstants.FIVE_EUR_INTEGER;
import static by.lesharb.messages.MessageConstants.FIVE_RUB_FRACTION;
import static by.lesharb.messages.MessageConstants.FIVE_RUB_INTEGER;
import static by.lesharb.messages.MessageConstants.FIVE_UAH_FRACTION;
import static by.lesharb.messages.MessageConstants.FIVE_UAH_INTEGER;
import static by.lesharb.messages.MessageConstants.FIVE_USD_FRACTION;
import static by.lesharb.messages.MessageConstants.FIVE_USD_INTEGER;
import static by.lesharb.messages.MessageConstants.ONE_EUR_FRACTION;
import static by.lesharb.messages.MessageConstants.ONE_EUR_INTEGER;
import static by.lesharb.messages.MessageConstants.ONE_RUB_FRACTION;
import static by.lesharb.messages.MessageConstants.ONE_RUB_INTEGER;
import static by.lesharb.messages.MessageConstants.ONE_UAH_FRACTION;
import static by.lesharb.messages.MessageConstants.ONE_UAH_INEGER;
import static by.lesharb.messages.MessageConstants.ONE_USD_FRACTION;
import static by.lesharb.messages.MessageConstants.ONE_USD_INTEGER;
import static by.lesharb.messages.MessageConstants.TWO_EUR_FRACTION;
import static by.lesharb.messages.MessageConstants.TWO_EUR_INTEGER;
import static by.lesharb.messages.MessageConstants.TWO_RUB_FRACTION;
import static by.lesharb.messages.MessageConstants.TWO_RUB_INTEGER;
import static by.lesharb.messages.MessageConstants.TWO_UAH_FRACTION;
import static by.lesharb.messages.MessageConstants.TWO_UAH_INTEGER;
import static by.lesharb.messages.MessageConstants.TWO_USD_FRACTION;
import static by.lesharb.messages.MessageConstants.TWO_USD_INTEGER;

import com.google.common.base.Verify;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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

    private static final Currency UAH = Currency.builder()
            .code(980)
            .name("UAH")
            .oneInteger(ONE_UAH_INEGER)
            .twoIntegers(TWO_UAH_INTEGER)
            .fiveIntegers(FIVE_UAH_INTEGER)
            .integerSex(Sex.FEMALE)
            .oneFraction(ONE_UAH_FRACTION)
            .twoFractions(TWO_UAH_FRACTION)
            .fiveFractions(FIVE_UAH_FRACTION)
            .fractionSex(Sex.FEMALE)
            .build();

    private static final Currency EUR = Currency.builder()
            .code(978)
            .name("EUR")
            .oneInteger(ONE_EUR_INTEGER)
            .twoIntegers(TWO_EUR_INTEGER)
            .fiveIntegers(FIVE_EUR_INTEGER)
            .integerSex(Sex.MALE)
            .oneFraction(ONE_EUR_FRACTION)
            .twoFractions(TWO_EUR_FRACTION)
            .fiveFractions(FIVE_EUR_FRACTION)
            .fractionSex(Sex.MALE)
            .build();

    private static final Currency USD = Currency.builder()
            .code(840)
            .name("USD")
            .oneInteger(ONE_USD_INTEGER)
            .twoIntegers(TWO_USD_INTEGER)
            .fiveIntegers(FIVE_USD_INTEGER)
            .integerSex(Sex.MALE)
            .oneFraction(ONE_USD_FRACTION)
            .twoFractions(TWO_USD_FRACTION)
            .fiveFractions(FIVE_USD_FRACTION)
            .fractionSex(Sex.MALE)
            .build();

    private static final Currency RUB = Currency.builder()
            .code(643)
            .name("RUB")
            .oneInteger(ONE_RUB_INTEGER)
            .twoIntegers(TWO_RUB_INTEGER)
            .fiveIntegers(FIVE_RUB_INTEGER)
            .integerSex(Sex.MALE)
            .oneFraction(ONE_RUB_FRACTION)
            .twoFractions(TWO_RUB_FRACTION)
            .fiveFractions(FIVE_RUB_FRACTION)
            .fractionSex(Sex.FEMALE)
            .build();

    private static final List<Currency> currencies = new CopyOnWriteArrayList<Currency>() {{
        add(UAH);
        add(EUR);
        add(USD);
        add(RUB);
    }};


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

}
