package com.norah.albaqami.warmhaven.pet.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://warm-haven-4058f-default-rtdb.firebaseio.com/"
/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
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
    suspend fun getPets() : List<PetItem>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object PetsApi {
    val retrofitService: PetService by lazy { retrofit.create(PetService::class.java) }
}