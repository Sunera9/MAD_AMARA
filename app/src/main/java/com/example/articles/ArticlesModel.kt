package com.example.articles

data class ArticlesModel(

    var articleId:String?=null,
    var title: String? = null,
    var content: String? = null,
    var type: String? = null,
    var imageUrl: String? = null
) {
    constructor() : this(null, null, null, null, null)
}
