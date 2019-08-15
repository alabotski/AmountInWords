package by.lesharb.controller;

import by.lesharb.currency.Currency;
import by.lesharb.currency.CurrencyEnum;
import by.lesharb.currency.CurrencyList;
import by.lesharb.currency.CurrencyUtil;
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
@Controller("/amount")
@Validated
@Slf4j
public class AmountController {

    @Post(uri = "/{currency}", produces = MediaType.TEXT_PLAIN)
    public Single<String> addCurrency(@NotBlank String currency) {
        CurrencyEnum currencyEnum = CurrencyUtil.getCurrency(currency);
        Currency currencyAdd = CurrencyUtil.readCurrency(currencyEnum);
        CurrencyList.addCurrency(currencyAdd);
        return Single.just("Currency added successfully");
    }

    @Delete(uri = "/{currency}", produces = MediaType.TEXT_PLAIN)
    public Single<String> deleteCurrency(@NotBlank String currency) {
        CurrencyEnum currencyEnum = CurrencyUtil.getCurrency(currency);
        Currency currencyRemove = CurrencyUtil.readCurrency(currencyEnum);
        CurrencyList.removeCurrency(currencyRemove);
        return Single.just("Currency delete successfully");
    }

    @Get(uri = "/{amount}", produces = MediaType.TEXT_PLAIN)
    public Single<String> amountInWord(@NotBlank String amount) {
        return Single.just("Hello " + amount + "!");
    }
}
