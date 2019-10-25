package by.lesharb.dto;

import org.immutables.gson.Gson.TypeAdapters;
import org.immutables.value.Value.Immutable;

/**
 * Class which represents  Fields other than <code>code</code> and <code>name</code> are used for morphology.
 *
 * @author Aliaksei abotski.
 * @since 2019-08-14.
 */
@Immutable
@TypeAdapters
public abstract class Currency {

    // Digit currency code
    public abstract int code();

    // Symbol currency code
    public abstract String name();

    // Integer part morphology variables
    public abstract String oneInteger();

    public abstract String twoIntegers();

    public abstract String fiveIntegers();

    public abstract Sex integerSex();

    // Fraction part morphology variables
    public abstract String oneFraction();

    public abstract String twoFractions();

    public abstract String fiveFractions();

    public abstract Sex fractionSex();

    public enum Sex {
        MALE,
        FEMALE;
    }

    // public Currency clone() {
    //     return builder()
    //             .code(getCode())
    //             .name(getName())
    //             .oneInteger(getOneInteger())
    //             .twoIntegers(getTwoIntegers())
    //             .fiveIntegers(getFiveIntegers())
    //             .integerSex(getIntegerSex())
    //             .oneFraction(getOneFraction())
    //             .twoFractions(getTwoFractions())
    //             .fiveFractions(getFiveFractions())
    //             .fractionSex(getFractionSex())
    //             .build();
    // }

}