package com.example.kotlintv.model

import java.util.ArrayList

object OfferList {
    val OFFER_CATEGORY = arrayOf(
        "Offer 1",
        "Offer 2",
        "Category Two",
        "Category Three",
        "Category Four",
        "Category Five"
    )
    private var  list:List<Offer>? = null
    private var count: Long = 0
    fun getList(): List<Offer>? {
        if (list == null) {
            list = setupOfferss()
        }
        return list
    }

    fun setupOfferss(): List<Offer>? {
        list = ArrayList()
        val title = arrayOf(
            "100 Percentage OFF",
            "90 Percentage OFF",
            "80 Percentage OFF",
            "70 Percentage OFF",
            "60 Percentage OFF",
            "50 Percentage OFF",
            "40 Percentage OFF",
            "30 Percentage OFF",
            "20 Percentage OFF",
            "10 Percentage OFF"
        )

        val tag = arrayOf(
            "100OFF", "90OFF", "80OFF", "70OFF", "60OFF","50OFF","40OFF","30OFF","20OFF","10OFF"
        )
        val description = arrayOf(
                "100 Percentage OFF , offer applicable for all Premium Shows and Movies",
        "90 Percentage OFF , offer applicable for all Premium Shows and Movies",
        "80 Percentage OFF , offer applicable for all Premium Shows and Movies",
        "70 Percentage OFF , offer applicable for all Premium Shows and Movies",
        "60 Percentage OFF , offer applicable for all Premium Shows and Movies",
        "50 Percentage OFF , offer applicable for all Premium Shows and Movies",
        "40 Percentage OFF , offer applicable for all Premium Shows and Movies",
        "30 Percentage OFF , offer applicable for all Premium Shows and Movies",
        "20 Percentage OFF , offer applicable for all Premium Shows and Movies",
        "10 Percentage OFF , offer applicable for all Premium Shows and Movies"
        )


        val validDate = arrayOf(
            "30/01/2022",
            "30/02/2022",
            "30/03/2022",
            "30/04/2022",
            "30/05/2022",
            "30/06/2022",
            "30/07/2022",
            "30/08/2022",
            "30/09/2022",
            "30/10/2022",
        )

        for (index in title.indices) {
            (list as ArrayList<Offer>).add(
                buildOffersInfo(
                    title[index],
                    tag[index] ,
                    description[index],
                    validDate[index] ,
                )
            )
        }
        return list
    }

    private fun buildOffersInfo(
        title: String,
        tag:String,
        description: String,
        validDate:String
    ): Offer {
        val offers = Offer()
        offers.id = count++
        offers.title = title
        offers.tag = tag
        offers.description = description
        offers.validDate = validDate
        return offers
    }
}