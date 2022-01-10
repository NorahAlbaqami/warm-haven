package com.norah.albaqami.warmhaven.pet.data

import com.norah.albaqami.warmhaven.network.AnnouncementItem
import com.norah.albaqami.warmhaven.network.PetItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
interface NetworkService {

    @GET("pet.json")
    suspend fun getPets() : Map<String, PetItem>
    @GET("pet/id.json")
    suspend fun getItemById(@Query("id") petId: String) : Map<String, PetItem>
    @GET("announcement.json")
    suspend fun getAnnouncements() : Map<String, AnnouncementItem>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object Api {
    val retrofitService: NetworkService by lazy { retrofit.create(NetworkService::class.java) }
}