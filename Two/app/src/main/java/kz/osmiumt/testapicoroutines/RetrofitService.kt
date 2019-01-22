package kz.osmiumt.testapicoroutines

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    @GET("/posts")
    fun getPost(): Deferred<List<Post>>
}