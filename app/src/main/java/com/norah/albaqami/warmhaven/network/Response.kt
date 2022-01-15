package com.norah.albaqami.warmhaven.network

import com.squareup.moshi.Json


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
	val userId: String?=null
)
data class AnnouncementItem(val id: String? = null,
							val userId: String? ,
							val title : String? = null,
							val description: String? = null,
							val location: String? = null,
							val image: String? = null,
							val phone: String? = null)

