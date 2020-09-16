package com.rn7417.rmaseptembar.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.rn7417.rmaseptembar.MainActivity
import com.rn7417.rmaseptembar.R
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity(R.layout.activity_login) {


    companion object {
        var MESSAGE = ""
        val DURATION = Toast.LENGTH_SHORT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prijavaBtn.setOnClickListener{
            if(firstnameTv.text.toString().isNotEmpty() && passwordTv.text.toString().isNotEmpty()) {

                val settings2 = getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
                val firstName = settings2.getString("firstname", "");
                val password = settings2.getString("password", "");
                if(firstnameTv.text.toString() == firstName && passwordTv.text.toString() == password)
                {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    MESSAGE = "Neispravan unos"
                    popupToast()
                }
            }
            else {
                MESSAGE = "Neispravan unos"
                popupToast()
            }
        }
        registerTv.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    private fun popupToast(){
        val toast = Toast.makeText(applicationContext,
            MESSAGE,
            DURATION
        )
        toast.show()
    }
}