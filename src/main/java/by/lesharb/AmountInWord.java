package by.lesharb;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * AmountInWord application.
 *
 * @author Aliaksei Labotski.
 * @since 2019-08-14.
 */
@Slf4j
public class AmountInWord {

    public static void main(String[] args) {
        log.info("=== START ===");

        log.info("Amount = " + 1234567890L);
        Map<String, String> currencyMap = CurrencyService.format(1234567890L);
        log(currencyMap);

        log.info("Amount = " + 987654321L);
        currencyMap = CurrencyService.format(987654321L);
        log(currencyMap);

        log.info("Amount = " + 100000100101111L);
        currencyMap = CurrencyService.format(100000100101111L);
        log(currencyMap);

        log.info("Amount = " + 123321123321L);
        currencyMap = CurrencyService.format(123321123321L);
        log(currencyMap);

        log.info("=== FINISH ===");
    }

    private static void log(Map<String, String> map) {
        map.forEach((key, value) -> log.info(key + ":" + value));
    }
}
