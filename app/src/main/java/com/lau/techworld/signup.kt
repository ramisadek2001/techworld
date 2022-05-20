package com.lau.techworld

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.regex.Matcher
import java.util.regex.Pattern

public class signup : AppCompatActivity() {


    val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://techworld-69698-default-rtdb.firebaseio.com")
    val fauth : FirebaseAuth = FirebaseAuth.getInstance()
    val fStore = Firebase.firestore
    lateinit var userID : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val fullname = findViewById<EditText>(R.id.Fullname)
        val email = findViewById<EditText>(R.id.email)
        val phone = findViewById<EditText>(R.id.Phone)
        val password = findViewById<EditText>(R.id.password)
        val conPassword = findViewById<EditText>(R.id.conpassword)

        val registerBtn = findViewById<Button>(R.id.RegisterBtn)
        val loginNowBtn = findViewById<TextView>(R.id.loginNow)


        registerBtn.setOnClickListener(object : View.OnClickListener{

            override fun onClick(p0: View?) {
                val fullmameTxt = fullname.text.toString()
                val emailTxt = email.text.toString()
                val phoneTxt = phone.text.toString()
                var passwordTxt = password.text.toString()
                val conPasswordTxt = conPassword.text.toString()

                if (fullmameTxt.isEmpty()|| emailTxt.isEmpty()|| phoneTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(applicationContext,"Please fill all fields", Toast.LENGTH_SHORT).show()
                }
                else if(!isEmailValid(emailTxt)){
                    Toast.makeText(applicationContext, "Your Email is Invalid.", Toast.LENGTH_SHORT).show()
                    Log.e("Email Failed","Email Failed")
                }

                else if(!passwordTxt.equals(conPassword.text.toString())){
                    Toast.makeText(applicationContext,"Passwords are not matching", Toast.LENGTH_SHORT).show()
                    }
                else{
                    fauth.createUserWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                        override fun onComplete(p0: Task<AuthResult>) {
                            if (p0.isSuccessful){

                                userID = fauth.currentUser!!.uid


                                val df : DocumentReference = fStore.collection("users").document(userID)

                                val user = hashMapOf<String, String>()
                                user.put("fName",fullmameTxt)
                                user.put("email", emailTxt)
                                user.put("phone",phoneTxt)
                                user.put("password",passwordTxt)
                                user.put("addtitle", "")
                                user.put("building","")
                                user.put("apartment","")
                                user.put("instructions","")
                                user.put("street","")



                                df.set(user).addOnSuccessListener {
                                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                                }


                                val int : Intent = Intent(applicationContext,login_page::class.java)
                                startActivity(int)
                            }else{
                                Toast.makeText(applicationContext, "Error!"+ p0.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }

                    })
                }





            }
        })
        loginNowBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                finish()
            }

        })

    }
    private fun isEmailValid(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}