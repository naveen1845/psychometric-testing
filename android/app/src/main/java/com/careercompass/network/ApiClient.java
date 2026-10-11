package com.careercompass.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Singleton Retrofit HTTP Client.
 *
 * NOTE FOR EMULATOR VS PHYSICAL DEVICE:
 * - When running in the Android Studio Emulator, use "http://10.0.2.2:8080/api/"
 *   (10.0.2.2 is the special alias mapped to the host machine's localhost 127.0.0.1).
 * - When running on a physical Android phone via Wi-Fi, replace with your host IP,
 *   e.g. "http://192.168.1.100:8080/api/".
 */
public class ApiClient {

    // Default host IP for standard Android Emulator
    public static final String EMULATOR_BASE_URL = "http://10.0.2.2:8080/api/";

    private static String currentBaseUrl = EMULATOR_BASE_URL;
    private static Retrofit retrofit = null;
    private static ApiService apiService = null;

    public static synchronized void setBaseUrl(String newUrl) {
        if (newUrl != null && !newUrl.equals(currentBaseUrl)) {
            if (!newUrl.endsWith("/")) {
                newUrl += "/";
            }
            currentBaseUrl = newUrl;
            retrofit = null;
            apiService = null;
        }
    }

    public static String getBaseUrl() {
        return currentBaseUrl;
    }

    public static synchronized ApiService getService() {
        if (apiService == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(currentBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
