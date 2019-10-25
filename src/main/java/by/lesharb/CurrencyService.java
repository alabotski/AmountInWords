package by.lesharb;

import static by.lesharb.messages.MessageConstants.WORD_0;
import static by.lesharb.messages.MessageConstants.WORD_10;
import static by.lesharb.messages.MessageConstants.WORD_100;
import static by.lesharb.messages.MessageConstants.WORD_11;
import static by.lesharb.messages.MessageConstants.WORD_12;
import static by.lesharb.messages.MessageConstants.WORD_13;
import static by.lesharb.messages.MessageConstants.WORD_14;
import static by.lesharb.messages.MessageConstants.WORD_15;
import static by.lesharb.messages.MessageConstants.WORD_16;
import static by.lesharb.messages.MessageConstants.WORD_17;
import static by.lesharb.messages.MessageConstants.WORD_18;
import static by.lesharb.messages.MessageConstants.WORD_19;
import static by.lesharb.messages.MessageConstants.WORD_1_FEMALE;
import static by.lesharb.messages.MessageConstants.WORD_1_MALE;
import static by.lesharb.messages.MessageConstants.WORD_20;
import static by.lesharb.messages.MessageConstants.WORD_200;
import static by.lesharb.messages.MessageConstants.WORD_2_FEMALE;
import static by.lesharb.messages.MessageConstants.WORD_2_MALE;
import static by.lesharb.messages.MessageConstants.WORD_3;
import static by.lesharb.messages.MessageConstants.WORD_30;
import static by.lesharb.messages.MessageConstants.WORD_300;
import static by.lesharb.messages.MessageConstants.WORD_4;
import static by.lesharb.messages.MessageConstants.WORD_40;
import static by.lesharb.messages.MessageConstants.WORD_400;
import static by.lesharb.messages.MessageConstants.WORD_5;
import static by.lesharb.messages.MessageConstants.WORD_50;
import static by.lesharb.messages.MessageConstants.WORD_500;
import static by.lesharb.messages.MessageConstants.WORD_6;
import static by.lesharb.messages.MessageConstants.WORD_60;
import static by.lesharb.messages.MessageConstants.WORD_600;
import static by.lesharb.messages.MessageConstants.WORD_7;
import static by.lesharb.messages.MessageConstants.WORD_70;
import static by.lesharb.messages.MessageConstants.WORD_700;
import static by.lesharb.messages.MessageConstants.WORD_8;
import static by.lesharb.messages.MessageConstants.WORD_80;
import static by.lesharb.messages.MessageConstants.WORD_800;
import static by.lesharb.messages.MessageConstants.WORD_9;
import static by.lesharb.messages.MessageConstants.WORD_90;
import static by.lesharb.messages.MessageConstants.WORD_900;
import static by.lesharb.util.CurrencyConstant.BILLION;
import static by.lesharb.util.CurrencyConstant.MILLION;
import static by.lesharb.util.CurrencyConstant.THOUSAND;
import static by.lesharb.util.CurrencyConstant.TRILLION;
import static by.lesharb.util.GsonUtil.getGson;

import by.lesharb.dto.Currency;
import by.lesharb.dto.Currency.Sex;
import by.lesharb.dto.Triad;
import com.google.common.base.Charsets;
import com.google.common.base.Verify;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Service for work with collection which holds all registered currencies.
 *
 * @author Aliaksei Labotski.
 * @since 25/10/19.
 */
@Slf4j
public final class CurrencyService {

    private static final Gson GSON = getGson();
    private static final String CURRENCY_PATH = "currency/";
    private static final List<Currency> CURRENCY_LIST;

    static {
        List<String> currencyFileList = Collections.emptyList();
        try {
            currencyFileList = IOUtils.readLines(CurrencyService.class
                    .getClassLoader()
                    .getResourceAsStream(CURRENCY_PATH), Charsets.UTF_8);
        } catch (IOException ex) {
            log.error("Error get list of currencies");
        }
        CURRENCY_LIST = currencyFileList.stream()
                .map(currencyFile -> readCurrency(currencyFile))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static Currency getCurrencyByCode(int code) {
        return CURRENCY_LIST.stream()
                .filter(currency -> currency.code() == code)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Can't find currency with code -" + code));
    }

    public static Currency getCurrencyByCode(String code) {
        Verify.verify(NumberUtils.isCreatable(code), "The code must be a number");
        int codeInt = Integer.parseInt(code);
        return getCurrencyByCode(codeInt);
    }

    public static Currency getCurrencyByName(String name) {
        return CURRENCY_LIST.stream()
                .filter(currency -> currency.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Can't find currency with name -" + name));
    }

    public static Map<String, String> format(long amount) {
        return CURRENCY_LIST.stream()
                .collect(Collectors.toMap(Currency::name, currency -> format(amount, currency)));
    }

    public static String format(long amount, Currency currency) {
        if (amount > 99999999999999999L || amount < -99999999999999999L) {
            throw new UnsupportedOperationException("Amounts grater than 999'999'999'999'999.00 are not supported.");
        }
        Verify.verifyNotNull(currency, "Currency cann't be null");

        boolean notEmpty = (amount / 100 / 1000) == 0;

        Triad integerUnits = new Triad(currency.oneInteger(), currency.twoIntegers(), currency.fiveIntegers(),
                currency.integerSex(), notEmpty);
        Triad fractionUnits = new Triad(currency.oneFraction(), currency.twoFractions(), currency.fiveFractions(),
                currency.fractionSex(), true) {
            @Override
            public int getTriadFromAmount(long amount) {
                return (int) (amount % 100);
            }
        };

        List<Triad> triads = Arrays.asList(TRILLION, BILLION, MILLION, THOUSAND, integerUnits, fractionUnits);

        StringBuilder amountInWords = new StringBuilder();
        for (Triad triad : triads) {
            amountInWords.append(triadToWord(triad, triad.getTriadFromAmount(amount)));
        }

        return amountInWords.toString();
    }

    private static String triadToWord(Triad triad, int value) {
        StringBuilder builder = new StringBuilder();

        if (value == 0) {
            if (!triad.isMandatory()) {
                return "";
            }

            if (triad.isZero()) {
                return WORD_0 + " " + ending(triad, value);
            } else {
                return ending(triad, value);
            }
        }

        int hundreds = value / 100;
        int tens = (value % 100) / 10;
        int units = value % 10;

        switch (hundreds) {
            default:
                break;
            case 1:
                builder.append(WORD_100);
                break;
            case 2:
                builder.append(WORD_200);
                break;
            case 3:
                builder.append(WORD_300);
                break;
            case 4:
                builder.append(WORD_400);
                break;
            case 5:
                builder.append(WORD_500);
                break;
            case 6:
                builder.append(WORD_600);
                break;
            case 7:
                builder.append(WORD_700);
                break;
            case 8:
                builder.append(WORD_800);
                break;
            case 9:
                builder.append(WORD_900);
                break;
        }
        if (hundreds > 0) {
            builder.append(' ');
        }

        switch (tens) {
            default:
                break;
            case 2:
                builder.append(WORD_20);
                break;
            case 3:
                builder.append(WORD_30);
                break;
            case 4:
                builder.append(WORD_40);
                break;
            case 5:
                builder.append(WORD_50);
                break;
            case 6:
                builder.append(WORD_60);
                break;
            case 7:
                builder.append(WORD_70);
                break;
            case 8:
                builder.append(WORD_80);
                break;
            case 9:
                builder.append(WORD_90);
                break;
        }

        if (tens == 1) {
            switch (units) {
                case 0:
                    builder.append(WORD_10);
                    break;
                case 1:
                    builder.append(WORD_11);
                    break;
                case 2:
                    builder.append(WORD_12);
                    break;
                case 3:
                    builder.append(WORD_13);
                    break;
                case 4:
                    builder.append(WORD_14);
                    break;
                case 5:
                    builder.append(WORD_15);
                    break;
                case 6:
                    builder.append(WORD_16);
                    break;
                case 7:
                    builder.append(WORD_17);
                    break;
                case 8:
                    builder.append(WORD_18);
                    break;
                case 9:
                    builder.append(WORD_19);
                    break;
            }
        }
        if (tens > 0) {
            builder.append(' ');
        }

        if (tens != 1) {
            switch (units) {
                default:
                    break;
                case 1:
                    builder.append(triad.getSex().equals(Sex.MALE) ? WORD_1_MALE : WORD_1_FEMALE);
                    break;
                case 2:
                    builder.append(triad.getSex().equals(Sex.MALE) ? WORD_2_MALE : WORD_2_FEMALE);
                    break;
                case 3:
                    builder.append(WORD_3);
                    break;
                case 4:
                    builder.append(WORD_4);
                    break;
                case 5:
                    builder.append(WORD_5);
                    break;
                case 6:
                    builder.append(WORD_6);
                    break;
                case 7:
                    builder.append(WORD_7);
                    break;
                case 8:
                    builder.append(WORD_8);
                    break;
                case 9:
                    builder.append(WORD_9);
                    break;
            }
            if (units > 0) {
                builder.append(' ');
            }
        }

        builder.append(ending(triad, value));

        return builder.toString();
    }

    private static String ending(Triad triad, int value) {
        int tens = (value % 100) / 10;
        int units = value % 10;

        if (tens == 1) {
            return triad.getFive() + " ";
        }

        String ending;
        switch (units) {
            default:
                ending = triad.getFive();
                break;
            case 1:
                ending = triad.getOne();
                break;
            case 2:
            case 3:
            case 4:
                ending = triad.getTwo();
                break;
        }
        return ending + " ";
    }

    private static Currency readCurrency(String currencyFileName) {
        try (InputStream is = CurrencyService.class.getClassLoader()
                .getResourceAsStream(CURRENCY_PATH + currencyFileName)) {
            String json = IOUtils.toString(is, StandardCharsets.UTF_8.name());
            return GSON.fromJson(json, Currency.class);
        } catch (IOException ex) {
            log.error("Error read file - {}", currencyFileName, ex);
            return null;
        }
    }
}
