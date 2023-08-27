package com.rkbapps.pixy.api

import com.rkbapps.pixy.categories.models.Topic
import com.rkbapps.pixy.home.models.PhotosItem
import com.rkbapps.pixy.imagedetails.models.Photo
import com.rkbapps.pixy.search.model.SearchResult
import com.rkbapps.pixy.utils.ApiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashAPI {
    @GET("photos")
    suspend fun getPhotoList(
        @Query("per_page") perPage: Int = 20,
        @Query("client_id") clientId: String = ApiData.ACCESS_KEY,
        @Query("page") page: Int = 1,
    ): Response<List<PhotosItem>>

    @GET("topics")
    suspend fun getTopicList(
        @Query("per_page") perPage: Int = 30,
        @Query("client_id") clientId: String = ApiData.ACCESS_KEY,
    ): Response<List<Topic>>

    @GET("collections")
    suspend fun getCollection(
        @Query("per_page") perPage: Int = 30,
        @Query("client_id") clientId: String = ApiData.ACCESS_KEY,
    ): Response<List<Topic>>

    @GET("photos/{id}")
    suspend fun getAPhoto(
        @Path("id") id: String,
        @Query("client_id") clientId: String = ApiData.ACCESS_KEY,
    ): Response<Photo>

    @GET("topics/{id}/photos")
    suspend fun getTopicPhotos(
        @Path("id") id: String,
        @Query("per_page") perPage: Int = 20,
        @Query("client_id") clientId: String = ApiData.ACCESS_KEY,
        @Query("page") page: Int = 1,
    ): Response<List<Photo>>

    @GET("collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Path("id") id: String,
        @Query("per_page") perPage: Int = 20,
        @Query("client_id") clientId: String = ApiData.ACCESS_KEY,
        @Query("page") page: Int = 1,
    ): Response<List<Photo>>

    @GET("search/photos")
    suspend fun getSearchResult(
        @Query("per_page") perPage: Int = 20,
        @Query("client_id") clientId: String = ApiData.ACCESS_KEY,
        @Query("page") page: Int = 1,
        @Query("query") query: String,
    ): Response<SearchResult>


}