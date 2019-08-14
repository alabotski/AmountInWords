package by.lesharb.currency;

import java.util.Objects;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Data;


/**
 * Class which represents Currency. Fields other than <code>code</code> and <code>name</code> are used for morphology.
 *
 * @author Aliaksei abotski.
 * @since 2019-08-14.
 */
@Data
@Builder
class Currency implements Cloneable {

    // Digit currency code
    private Integer code;

    // Symbol currency code
    private String name;

    // Integer part morphology variables
    private String oneInteger;
    private String twoIntegers;
    private String fiveIntegers;
    private Sex integerSex;

    // Fraction part morphology variables
    private String oneFraction;
    private String twoFractions;
    private String fiveFractions;
    private Sex fractionSex;

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

    @Override
    public Currency clone() {
        return Currency.builder()
                .code(getCode())
                .name(getName())
                .oneInteger(getOneInteger())
                .twoIntegers(getTwoIntegers())
                .fiveIntegers(getFiveIntegers())
                .integerSex(getIntegerSex())
                .oneFraction(getOneFraction())
                .twoFractions(getTwoFractions())
                .fiveFractions(getFiveFractions())
                .fractionSex(getFractionSex())
                .build();
    }
}
