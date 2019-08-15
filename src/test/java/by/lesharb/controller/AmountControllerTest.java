package by.lesharb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-14.
 */
@Slf4j
@MicronautTest
class AmountControllerTest {

    @Inject
    private AmountClient amountClient;

    @Test
    void amountInWord() {
        String response = amountClient.amountInWord("Fred").blockingGet();
        assertEquals("Hello Fred!", response);
    }

    @Test
    void addCurrency() {
        String response = amountClient.addCurrency("UAH").blockingGet();
        assertEquals("Currency added successfully", response);
    }

    @Test
    void deleteCurrency() {
        String response = amountClient.deleteCurrency("UAH").blockingGet();
        assertEquals("Currency delete successfully", response);
    }

    @Client("/amount")
    private interface AmountClient {

        @Post("/{currency}")
        Single<String> addCurrency(@NotBlank String currency);

        @Delete("/{currency}")
        Single<String> deleteCurrency(@NotBlank String currency);

        @Get("/{amount}")
        Single<String> amountInWord(@NotBlank String amount);
    }
}