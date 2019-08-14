package by.lesharb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import io.reactivex.Single;
import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;

/**
 * @author Aliaksei Labotski.
 * @since 2019-08-14.
 */
@MicronautTest
class AmountControllerTest {

    @Inject
    private AmountClient amountClient;

    @Test
    void amountInWord() {
        assertEquals("Hello Fred!", amountClient.amountInWord("Fred").blockingGet());
    }

    @Client("/")
    interface AmountClient {

        @Get("/amount/{amount}")
        Single<String> amountInWord(@NotBlank String amount);
    }
}