package by.lesharb.service.currency;

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

import by.lesharb.dto.Currency.Sex;
import by.lesharb.dto.Triad;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-16.
 */
public class CurrencyConstant {

    public static final Triad THOUSAND = new Triad(THOUSAND_ONE, THOUSAND_TWO, THOUSAND_FIVE, Sex.FEMALE, 3);
    public static final Triad MILLION = new Triad(MILLION_ONE, MILLION_TWO, MILLION_FIVE, Sex.MALE, 6);
    public static final Triad BILLION = new Triad(BILLION_ONE, BILLION_TWO, BILLION_FIVE, Sex.MALE, 9);
    public static final Triad TRILLION = new Triad(TRILLION_ONE, TRILLION_TWO, TRILLION_FIVE, Sex.MALE, 12);
}
