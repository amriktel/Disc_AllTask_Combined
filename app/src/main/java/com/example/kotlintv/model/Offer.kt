package com.example.kotlintv.model

import java.io.Serializable

data class Offer(var id: Long = 0,
                 var title: String? = null,
                 var tag: String? = null,
                 var description: String? = null,
                 var validDate: String? = null,

) : Serializable {

    override fun toString(): String {
        return "Offer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                ", validDate='" + validDate + '\'' +
                '}'
    }

    companion object {
        internal const val serialVersionUID = 727566175075960653L
    }
}