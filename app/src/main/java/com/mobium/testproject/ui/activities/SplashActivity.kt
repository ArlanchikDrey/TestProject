package com.mobium.testproject.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobium.testproject.R
import com.mobium.testproject.utils.Utils
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * для того, чтобы убедиться, что у user'а есть подключение и ему можно переходить в основную активность*/
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkUserConnection()

        btn_checkConnection.setOnClickListener {
            checkUserConnection()
        }
    }

    private fun checkUserConnection(){
        if (!Utils.hasConnection(applicationContext)){
            textCheckConnection.text = "Упс, у вас нет связи("
        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
