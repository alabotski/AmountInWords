package by.lesharb.dto;

import by.lesharb.dto.Currency.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-14.
 */
@Getter
@AllArgsConstructor
public class Triad {

    private String one;
    private String two;
    private String five;
    private Sex sex;
    // triad must be present in word representation
    private boolean mandatory;
    // zero triad value should not be omitted but represented as 0 instead
    private boolean zero;
    private long power = 0;

    public Triad(String one, String two, String five, Sex sex, long divisor) {
        this(one, two, five, sex, false, false, divisor);
    }

    public Triad(String one, String two, String five, Sex sex, boolean zero) {
        this(one, two, five, sex, true, zero, 0);
    }

    public int getTriadFromAmount(long amount) {
        long divisor = (long) Math.pow(10, power + 2);
        return (int) (amount / divisor % 1000);
    }
}