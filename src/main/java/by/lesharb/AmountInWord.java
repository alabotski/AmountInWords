package by.lesharb;

import io.micronaut.runtime.Micronaut;
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
        Micronaut.run(AmountInWord.class);
    }

}
