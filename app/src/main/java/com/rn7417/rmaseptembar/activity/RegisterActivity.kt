package com.rn7417.rmaseptembar.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rn7417.rmaseptembar.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(R.layout.activity_register) {

    companion object {
        var MESSAGE = ""
        val DURATION = Toast.LENGTH_SHORT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBtn.setOnClickListener{
            if(enterFirstnameTv.text.toString().isNotEmpty() && enterPasswordTv.text.toString().isNotEmpty() && reEnterPasswordTv.text.toString().isNotEmpty()) {
                if(enterPasswordTv.text.toString() == reEnterPasswordTv.text.toString()){
                    var upperCheck = false;
                    var digitHceck = false;
                    var emptyUsernameCheck = false;
                    var lengthCheck = enterPasswordTv.text.toString().length > 7
                    for(char in enterPasswordTv.text.toString()){
                        if(char.isUpperCase()) upperCheck = true;
                        if(char.isDigit()) digitHceck = true;
                    }
                    for(i in enterFirstnameTv.text.toString()){
                        if(i.isLetter()) emptyUsernameCheck = true
                    }
                    if(upperCheck){
                        if(digitHceck){
                            if(lengthCheck){
                                if(emptyUsernameCheck) {
                                    val settings = getSharedPreferences(
                                        "sharedPreferences",
                                        Context.MODE_PRIVATE
                                    )
                                    val editor = settings.edit()
                                    editor.clear()
                                    editor.putString("firstname", enterFirstnameTv.text.toString())
                                    editor.commit()
                                    editor.putString("password", enterPasswordTv.text.toString())
                                    editor.commit()
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                }
                                else{
                                    MESSAGE = "Username cannot be empty!"
                                    popupToast()
                                }
                            }
                            else{
                                MESSAGE = "Password must be at least 8 characters long!"
                                popupToast()
                            }
                        }
                        else{
                            MESSAGE = "Password must contain digit!"
                            popupToast()
                        }
                    }
                    else{
                        MESSAGE = "Password must contain UpperCase character!"
                        popupToast()
                    }
                }
                else{
                    MESSAGE = "Passwords do not match!"
                    popupToast()
                }

            }
            else {
                MESSAGE = "Wrong input!"
                popupToast()
            }
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
