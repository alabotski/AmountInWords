package by.lesharb.util;

import com.google.common.annotations.VisibleForTesting;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

/**
 * Manages an instance that will be initialized lazily in a thread safe manner. It is guaranteed that the instance will
 * only be created once and {@link #get()} will always return an initialized instance.
 *
 * @author Aliaksei Labotski.
 * @since 25/10/19.
 */
@RequiredArgsConstructor
public class LazySingleton<T> implements Supplier<T> {

    public static <T> LazySingleton<T> lazy(Supplier<T> supplier) {
        return new LazySingleton<>(supplier);
    }

    private final Object _lock = new Object();
    private final Supplier<T> _creator;
    private volatile T _instance;

    @Override
    public T get() {
        T v1 = _instance;
        if (v1 != null) {
            return v1;
        }

        synchronized (_lock) {
            T v2 = _instance;
            if (v2 != null) {
                return v2;
            }

            T v3 = _creator.get();
            _instance = v3;
            return v3;
        }
    }

    @VisibleForTesting
    public void reset() {
        synchronized (_lock) {
            _instance = null;
        }
    }

    @VisibleForTesting
    public void set(T value) {
        synchronized (_lock) {
            _instance = value;
        }
    }
}
