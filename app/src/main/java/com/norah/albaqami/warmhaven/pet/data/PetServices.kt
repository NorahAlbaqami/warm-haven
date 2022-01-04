package com.norah.albaqami.warmhaven.pet.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.okhttp.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://warm-haven-4058f-default-rtdb.firebaseio.com/"
/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
fun getlogger():HttpLoggingInterceptor{

    var interceptor =  HttpLoggingInterceptor();
    interceptor.level=HttpLoggingInterceptor.Level.BODY

return  interceptor
}

var client = okhttp3.OkHttpClient.Builder().addInterceptor(getlogger()).build();

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
/**
 * A public interface that exposes the [getPet] method
 */
interface PetService {
    /**
     * Returns a [List] of [pets] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "pet.json" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("pet.json")
    suspend fun getPets() : Map<String,PetItem>
    @GET("pet/id.json")
    suspend fun getItemById(@Query("id") petId: String) : Map<String,PetItem>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object PetsApi {
    val retrofitService: PetService by lazy { retrofit.create(PetService::class.java) }
}