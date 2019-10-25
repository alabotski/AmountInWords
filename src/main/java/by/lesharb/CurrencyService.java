package by.lesharb;

import by.lesharb.util.LazySingleton;

/**
 * Service for work with collection which holds all registered currencies.
 *
 * @author Aliaksei Labotski.
 * @since 25/10/19.
 */
public class CurrencyService {

    private static final LazySingleton<CurrencyService> INSTANCE = new LazySingleton(CurrencyService::new);

    public static CurrencyService instance() {
        return INSTANCE.get();
    }
}
