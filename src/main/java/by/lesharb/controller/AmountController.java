package by.lesharb.controller;

import by.lesharb.service.currency.CurrencyService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-14.
 */
@Controller("/amount")
@Validated
@Slf4j
public class AmountController {

    @Inject
    private CurrencyService currencyService;

    @Post(uri = "/{currency}", produces = MediaType.TEXT_PLAIN)
    public Single<String> addCurrency(@NotBlank String currency) {
        currencyService.addCurrency(currency);
        return Single.just("Currency added successfully");
    }

    @Delete(uri = "/{currency}", produces = MediaType.TEXT_PLAIN)
    public Single<String> deleteCurrency(@NotBlank String currency) {
        currencyService.removeCurrency(currency);
        return Single.just("Currency delete successfully");
    }

    @Get(uri = "/{amount}", produces = MediaType.TEXT_PLAIN)
    public Single<String> amountInWord(@NotBlank String amount) {
        return Single.just("Hello " + amount + "!");
    }
}
