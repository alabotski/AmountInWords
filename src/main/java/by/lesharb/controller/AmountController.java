package by.lesharb.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import io.reactivex.Single;
import javax.validation.constraints.NotBlank;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-14.
 */
@Controller("/")
@Validated
public class AmountController {

    @Get(uri = "/amount/{amount}", produces = MediaType.TEXT_PLAIN)
    public Single<String> amountInWord(@NotBlank String amount) {
        return Single.just("Hello " + amount + "!");
    }
}
