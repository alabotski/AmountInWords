package by.lesharb.currency;

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
class Currency {

    // Digit currency code
    private int code;

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
}
