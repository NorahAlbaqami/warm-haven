package com.norah.albaqami.warmhaven.pet.data

import com.squareup.moshi.Json

data class Response(

	@Json(name="data")
	val data: Data? = null
)

data class PetItem(

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: String? = null
)

data class Data(

	@Json(name="pet")
	val pet: List<PetItem?>? = null
)
