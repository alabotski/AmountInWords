package by.lesharb.currency;

import static by.lesharb.messages.MessageConstants.BILLION_FIVE;
import static by.lesharb.messages.MessageConstants.BILLION_ONE;
import static by.lesharb.messages.MessageConstants.BILLION_TWO;
import static by.lesharb.messages.MessageConstants.MILLION_FIVE;
import static by.lesharb.messages.MessageConstants.MILLION_ONE;
import static by.lesharb.messages.MessageConstants.MILLION_TWO;
import static by.lesharb.messages.MessageConstants.THOUSAND_FIVE;
import static by.lesharb.messages.MessageConstants.THOUSAND_ONE;
import static by.lesharb.messages.MessageConstants.THOUSAND_TWO;
import static by.lesharb.messages.MessageConstants.TRILLION_FIVE;
import static by.lesharb.messages.MessageConstants.TRILLION_ONE;
import static by.lesharb.messages.MessageConstants.TRILLION_TWO;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-14.
 */
class Triad {

    public static final Triad THOUSAND = new Triad(THOUSAND_ONE, THOUSAND_TWO, THOUSAND_FIVE, Sex.FEMALE, 3);
    public static final Triad MILLION = new Triad(MILLION_ONE, MILLION_TWO, MILLION_FIVE, Sex.MALE, 6);
    public static final Triad BILLION = new Triad(BILLION_ONE, BILLION_TWO, BILLION_FIVE, Sex.MALE, 9);
    public static final Triad TRILLION = new Triad(TRILLION_ONE, TRILLION_TWO, TRILLION_FIVE, Sex.MALE, 12);

    private Triad(String one, String two, String five, Sex sex, long divisor) {
        this(one, two, five, sex, false, false, divisor);
    }

    private Triad(String one, String two, String five, Sex sex, boolean zero) {
        this(one, two, five, sex, true, zero, 0);
    }

    private Triad(String one, String two, String five, Sex sex, boolean mandatory, boolean zero, long power) {
        this.one = one;
        this.two = two;
        this.five = five;
        this.sex = sex;
        this.mandatory = mandatory;
        this.zero = zero;
        this.power = power;
    }

    private String one;
    private String two;
    private String five;
    private Sex sex;
    // triad must be present in word representation
    private boolean mandatory;
    // zero triad value should not be omitted but represented as 0 instead
    private boolean zero;
    private long power = 0;

    public int getTriadFromAmount(long amount) {
        long divisor = (long) Math.pow(10, power + 2);
        return (int) (amount / divisor % 1000);
    }
}