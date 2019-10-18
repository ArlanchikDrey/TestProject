package com.mobium.testproject.data

import com.mobium.testproject.model.JobItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JobsApi{
    @GET("/positions.json")
    fun getJobs(@Query("search") search: String): Call<List<JobItem>>
}