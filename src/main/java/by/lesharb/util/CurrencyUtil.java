package by.lesharb.util;

import static by.lesharb.util.GsonUtil.getGson;

import by.lesharb.dto.Currency;
import com.google.common.base.Charsets;
import com.google.gson.Gson;
import io.vavr.control.Try;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

/**
 * @author Aliaksei Labotski.
 * @since 25/10/19.
 */
@Slf4j
public final class CurrencyUtil {

    private static final LazySingleton<CurrencyUtil> INSTANCE = new LazySingleton(CurrencyUtil::new);
    private static final Gson GSON = getGson();
    private static final String CURRENCY_PATH = "currency/";

    private final List<Currency> CURRENCY_LIST;

    private CurrencyUtil() {
        List<String> currencyFileList = Try.of(() -> IOUtils.readLines(CurrencyUtil.class
                .getClassLoader()
                .getResourceAsStream(CURRENCY_PATH), Charsets.UTF_8))
                .getOrElse(() -> {
                    log.error("Error get list of currencies");
                    return Collections.emptyList();
                });
        CURRENCY_LIST = currencyFileList.stream()
                .map(currencyFile -> readCurrency(currencyFile))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static CurrencyUtil instance() {
        return INSTANCE.get();
    }

    private Currency readCurrency(String currencyFileName) {
        try (InputStream is = CurrencyUtil.class.getClassLoader()
                .getResourceAsStream(CURRENCY_PATH + currencyFileName)) {
            String json = IOUtils.toString(is, StandardCharsets.UTF_8.name());
            return GSON.fromJson(json, Currency.class);
        } catch (IOException ex) {
            log.error("Error read file - {}", currencyFileName, ex);
            return null;
        }
    }
}
