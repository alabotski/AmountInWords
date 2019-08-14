package by.lesharb.controller;

import by.lesharb.currency.CurrencyEnum;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.reactivex.Single;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-14.
 */
@Controller("/")
@Validated
@Slf4j
public class AmountController {

    @Post(uri = "/amount/{currency}", produces = MediaType.TEXT_PLAIN)
    public Single<String> addCurrency(@NotBlank String currency) {
        CurrencyEnum currencyEnum = CurrencyEnum.valueOf(currency);
        if (currencyEnum == null) {
            String errMsg = "Current currency is not supported";
            log.error(errMsg);
            return Single.just(errMsg);
        } else {
            return null;
        }
    }

    @Delete(uri = "/amount/{currency}", produces = MediaType.TEXT_PLAIN)
    public Single<String> deleteCurrency(@NotBlank String currency) {
        return null;
    }

    @Get(uri = "/amount/{amount}", produces = MediaType.TEXT_PLAIN)
    public Single<String> amountInWord(@NotBlank String amount) {
        return Single.just("Hello " + amount + "!");
    }
}
