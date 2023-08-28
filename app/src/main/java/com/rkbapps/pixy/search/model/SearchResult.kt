package com.rkbapps.pixy.search.model

import com.rkbapps.pixy.imagedetails.models.Photo

data class SearchResult(
    val total: Int,
    val total_pages: Int,
    val results: List<Photo>,
){
    constructor():this(0,0, emptyList())
}
