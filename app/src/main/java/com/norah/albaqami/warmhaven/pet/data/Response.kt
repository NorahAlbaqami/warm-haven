package com.norah.albaqami.warmhaven.pet.data

import com.squareup.moshi.Json

data class Response(

	@Json(name="data")
	val data: Data? = null
)

data class PetItem(

	@Json(name = "image")
	val image: String? = null,

	@Json(name = "phone")
	val phone: String? = null,

	@Json(name = "name")
	val name: String? = null,

	@Json(name = "description")
	val description: String? = null,

	@Json(name = "location")
	val location: String? = null,

	@Json(name = "id")
	val id: String? = null,

	@Json(name = "type")
	val type: String? = null,
	val userId: String?
)

data class Data(

	@Json(name="pet")
	val pet: List<PetItem?>? = null
)
