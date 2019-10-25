package by.lesharb.util;

import by.lesharb.dto.GsonAdaptersCurrency;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import java.util.ServiceLoader;
import lombok.experimental.UtilityClass;

/**
 * @author Aliaksei Labotski.
 * @since 25/10/19.
 */
@UtilityClass
class GsonUtil {

    private static Gson gson;

    public static Gson getGson() {
        if (gson != null) {
            return gson;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        for (TypeAdapterFactory factory : ServiceLoader.load(TypeAdapterFactory.class)) {
            gsonBuilder.registerTypeAdapterFactory(factory);
        }

        // Manual registration is also an option
        gsonBuilder.registerTypeAdapterFactory(new GsonAdaptersCurrency());

        gson = gsonBuilder.create();
        return gson;
    }
}
