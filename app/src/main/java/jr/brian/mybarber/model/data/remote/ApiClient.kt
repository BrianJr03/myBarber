package jr.brian.mybarber.model.data.remote

import jr.brian.mybarber.model.data.Constant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val retrofit: Retrofit by lazy {
        val logInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY }

        val client = OkHttpClient.Builder().apply {
            addInterceptor(logInterceptor)
        }.build()

        val r = Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()

        r   // return Retrofit object
    }
}
