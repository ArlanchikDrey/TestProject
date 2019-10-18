package com.mobium.testproject.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.mobium.testproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_job_acitivity.*

class JobAcitivity : AppCompatActivity() {

    companion object{
        const val DESC = "DESC"
        const val LOGO = "LOGO"
        const val TITLE = "TITLE"
    }
    private var url: String? = null
    private var title: String? = null
    private var desc: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_acitivity)

        val intent = intent

        if (savedInstanceState == null){
            url = intent.getStringExtra(LOGO)
            title = intent.getStringExtra(TITLE)
            desc = intent.getStringExtra(DESC)
        }else{
            url = savedInstanceState.getString(LOGO)
            title = savedInstanceState.getString(TITLE)
            desc = savedInstanceState.getString(DESC)
        }
        initView()
    }

    private fun initView(){
        if (url != null){
            Picasso.get().load(url).resize(250,250).into(imageLogo)
        }
        textTitle.text = title
        textDescAll.text = Html.fromHtml(desc)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LOGO, url)
        outState.putString(TITLE, title)
        outState.putString(DESC, desc)
    }

}
