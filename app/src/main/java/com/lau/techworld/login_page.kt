package com.lau.techworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener




class login_page : AppCompatActivity() {

    val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://techworld-69698-default-rtdb.firebaseio.com/")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        var signup = signup()
        val phone = findViewById<EditText>(R.id.Phone)
        val password = findViewById<EditText>(R.id.password)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val fauth : FirebaseAuth = FirebaseAuth.getInstance()
        val registerNowBtn = findViewById<TextView>(R.id.registerNowBtn)

        loginBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val emailTxt = phone.text.toString()
                val PasswordTxt = password.text.toString()


                if (emailTxt.isEmpty()|| PasswordTxt.isEmpty()){

                    Toast.makeText(applicationContext,"Please enter your mobile or password",Toast.LENGTH_SHORT).show()
                }else{
                    fauth.signInWithEmailAndPassword(emailTxt, PasswordTxt).addOnCompleteListener(this@login_page) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = fauth.currentUser
                            startActivity(Intent(applicationContext,MainActivity::class.java))

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("Failed","Failed")

                            Toast.makeText(applicationContext, "Authentication failed.", Toast.LENGTH_SHORT).show()

                        }
                    }

                }



//                else{
//
//                    databaseReference.child("users").addListenerForSingleValueEvent(object : ValueEventListener{
//                        override fun onDataChange(snapshot: DataSnapshot) {
//
//                            if (snapshot.hasChild(phoneTxt)){
//                                val getPassword = snapshot.child(phoneTxt).child("password").getValue(String :: class.java)
//                                if (getPassword.toString().equals(PasswordTxt)){
//                                    Toast.makeText(applicationContext,"Successfully logged in",Toast.LENGTH_SHORT).show()
//                                    startActivity(Intent(applicationContext,MainActivity::class.java))
//                                    finish()
//                                }else{
//                                    Toast.makeText(applicationContext,"wrong password",Toast.LENGTH_SHORT).show()
//                                }
//                            }else{
//                                Toast.makeText(applicationContext,"wrong password",Toast.LENGTH_SHORT).show()
//                            }
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//
//                        }
//
//                    })
//
//                }

            }

        })
        registerNowBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {

                startActivity(Intent(applicationContext,signup::class.java))

            }

        })

    }
}