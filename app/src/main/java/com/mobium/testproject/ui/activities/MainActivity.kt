package com.mobium.testproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobium.testproject.data.NetworkService
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


class MainActivity : AppCompatActivity() {

    private lateinit var adapterJobs: AdapterJobs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mobium.testproject.R.layout.activity_main)

        initView()
    }

    private fun initView() {
        initRecycler()
        btn_search.setOnClickListener {
            connectedWithApi()
        }
    }

    //Отправляем запрос если есть интернет
    private fun connectedWithApi(){
        if (Utils.hasConnection(this)){
            progress_connected.visibility = View.VISIBLE
            NetworkService
                .getInstance()
                .jobsApi
                .getJobs(autoCompleteTextView.text.toString())
                .enqueue(object : Callback<List<JobItem>> {
                    override fun onResponse(call: Call<List<JobItem>>, response: Response<List<JobItem>>) {
                        val job = response.body()
                        if (job != null){
                            progress_connected.visibility = View.GONE
                        }
                        adapterJobs.update(job)
                    }

                    override fun onFailure(call: Call<List<JobItem>>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
                    }
                })
        }else{
            Snackbar.make(btn_search, "Нету связи",Snackbar.LENGTH_LONG).show()
        }

    }

    private fun initRecycler() {
        adapterJobs = AdapterJobs {
            val intent = Intent(this, JobAcitivity::class.java)
            intent.putExtra(JobAcitivity.DESC, it?.description)
            intent.putExtra(JobAcitivity.LOGO, it?.companyLogo)
            intent.putExtra(JobAcitivity.TITLE, it?.title)
            startActivity(intent)
        }
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        with(recyclerListJobs) {
            adapter = adapterJobs
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(divider)
        }
    }
}

