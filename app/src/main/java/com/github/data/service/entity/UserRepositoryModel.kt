package com.github.data.service.entity

import com.google.gson.annotations.SerializedName

data class UserRepositoryModel(
    @SerializedName("description") val description: String,
    @SerializedName("full_name") val full_name: String,
    @SerializedName("html_url") val html_url: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("node_id") val node_id: String
){

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserRepositoryModel

        if (description != other.description) return false
        if (full_name != other.full_name) return false
        if (html_url != other.html_url) return false
        if (id != other.id) return false
        if (name != other.name) return false
        if (node_id != other.node_id) return false

        return true
    }
}