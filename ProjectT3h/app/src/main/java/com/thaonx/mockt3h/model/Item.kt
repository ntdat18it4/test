package com.thaonx.mockt3h.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Item(
    var status: String,
    var articles: List<Articles>,
)

@Parcelize
data class Articles(
    var source: Sources,
    var title: String,
    var description: String,
    var urlToImage: String,
    var publishedAt: String,
    var url: String,
) : Parcelable

@Parcelize
data class Sources(
    var name: String,
) : Parcelable
