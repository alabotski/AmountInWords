package by.lesharb.currency;

import com.google.gson.Gson;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-14.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyUtil {

    private static final Gson gson = new Gson();
    private static final ClassPathResourceLoader loader = new ResourceResolver()
            .getLoader(ClassPathResourceLoader.class).get();

    public static boolean validate(Currency currency) {
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

    public static Currency clone(Currency currency) {
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

    public static Currency readCurrency(CurrencyEnum currencyEnum) {
        try {
            Optional<URL> optionalURL = loader.getResource("classpath:currency/" + currencyEnum.name() + ".json");
            URL url = optionalURL.orElseThrow(() -> new RuntimeException("No settings for this currency"));
            Reader uahReader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8.name());
            return gson.fromJson(uahReader, Currency.class);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            return Currency.builder().build();
        }
    }
}
