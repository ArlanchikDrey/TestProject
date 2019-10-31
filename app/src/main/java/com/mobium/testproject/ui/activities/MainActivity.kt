package com.mobium.testproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobium.testproject.ui.adapters.AdapterJobs
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.mobium.testproject.model.JobItem
import com.mobium.testproject.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.RecyclerView
import com.mobium.testproject.data.JobsApi
import com.mobium.testproject.data.components.DaggerJobsComponent
import com.mobium.testproject.data.modules.ApplicationContextModule
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var adapterJobs: AdapterJobs
    private var loading = true
    private var pastVisiblesItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private val myLinerLayoutManag = LinearLayoutManager(this@MainActivity)
    private lateinit var picasso: Picasso
    private lateinit var jobsApi: JobsApi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mobium.testproject.R.layout.activity_main)
        Log.d("tag","create")
        initView()
    }

    private fun initView() {
        btn_search.setOnClickListener {
            connectedWithApi()
        }
        scrollRecycler()
    }

    //Отправляем запрос если есть интернет
    private fun connectedWithApi(){
        if (Utils.hasConnection(this)){
            progress_connected.visibility = View.VISIBLE
            createConnected()
            createCalls()
        }else{
            Snackbar.make(btn_search, "Нету связи",Snackbar.LENGTH_LONG).show()
        }

    }

    private fun createCalls() {
        val jobsCalls = jobsApi.getJobs(autoCompleteTextView.text.toString())
        jobsCalls.enqueue(object : Callback<List<JobItem>> {
            override fun onResponse(call: Call<List<JobItem>>, response: Response<List<JobItem>>) {
                val job = response.body()
                if (job != null){
                    progress_connected.visibility = View.GONE
                }
                if(job?.size == 0)
                    Snackbar.make(btn_search, "Запрос неверный или такой вакансии нет",Snackbar.LENGTH_LONG).show()

                adapterJobs.update(job)
            }

            override fun onFailure(call: Call<List<JobItem>>, t: Throwable) {
                Snackbar.make(btn_search, "error \n ${t.message}",Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun createConnected(){
        Timber.plant(Timber.DebugTree())
        val jobsComponent = DaggerJobsComponent
            .builder()
            .applicationContextModule(ApplicationContextModule(this))
            .build()
        picasso = jobsComponent.getPicasso()
        jobsApi = jobsComponent.getJobsService()
        initRecycler()
    }
    private fun initRecycler() {
        adapterJobs = AdapterJobs(picasso) {
            val intent = Intent(this, JobAcitivity::class.java)
            intent.putExtra(JobAcitivity.DESC, it?.description)
            intent.putExtra(JobAcitivity.LOGO, it?.companyLogo)
            intent.putExtra(JobAcitivity.TITLE, it?.title)
            startActivity(intent)
        }
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        with(recyclerListJobs) {
            adapter = adapterJobs
            layoutManager = myLinerLayoutManag
            addItemDecoration(divider)
        }
    }

    //реализация бесконечного list'a
    private fun scrollRecycler(){
        recyclerListJobs.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0){
                    visibleItemCount = myLinerLayoutManag.childCount
                    totalItemCount = myLinerLayoutManag.itemCount
                    pastVisiblesItems = myLinerLayoutManag.findFirstVisibleItemPosition()

                    if (loading){
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount){
                            loading = false
                            Toast.makeText(this@MainActivity,"last",Toast.LENGTH_SHORT).show()
                            connectedWithApi()
                        }
                    }
                }
            }
        })
    }
}

