package com.lau.techworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lau.techworld.fragments.Fname
import com.lau.techworld.fragments.Pemail
import com.lau.techworld.fragments.Pnumber
import java.util.regex.Matcher
import java.util.regex.Pattern





class EditProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile2)

        val fname : EditText = findViewById(R.id.edname)
        val fauth : FirebaseAuth = FirebaseAuth.getInstance()
        val fStore = Firebase.firestore
        var userID = fauth.currentUser!!.uid
        val Phone: EditText = findViewById(R.id.edPhone)

        val email : EditText = findViewById(R.id.edemail)

        val savech = findViewById<AppCompatButton>(R.id.savech)
        val passwordtxt = findViewById<EditText>(R.id.password)
        val newemail = findViewById<EditText>(R.id.Newemail)
        val df : DocumentReference = fStore.collection("users").document(userID)
        val back = findViewById<ImageView>(R.id.leftIco)

        back.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext,MainActivity::class.java))
            }

        })

        df.addSnapshotListener(MetadataChanges.INCLUDE){ snapshot, e ->

            fname.setText(snapshot!!.getString("fName"))
            Phone.setText(snapshot!!.getString("phone"))
            email.setText(snapshot!!.getString("email"))

        }
        savech.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {



                val fullmameTxt = fname.text.toString()
                val emailTxt = email.text.toString()
                val phoneTxt = Phone.text.toString()
                val passwordt = passwordtxt.text.toString()
                val newemailtxt = newemail.text.toString()

                if(!isEmailValid(emailTxt)){
                    Toast.makeText(applicationContext, "Your Email is Invalid.", Toast.LENGTH_SHORT).show()
                }
                else if (fullmameTxt.isEmpty()|| emailTxt.isEmpty()|| phoneTxt.isEmpty()){
                    Toast.makeText(applicationContext,"Please fill all fields", Toast.LENGTH_SHORT).show()
                }
                else{


                    val df : DocumentReference = fStore.collection("users").document(userID)





                    if(newemailtxt.isNotEmpty()){

                        if (isEmailValid(newemailtxt)){

                            val user : FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser()

                            val credential = EmailAuthProvider.getCredential(emailTxt, passwordt);

                            user!!.reauthenticate(credential).addOnCompleteListener(this@EditProfile) { task ->

                                    // Sign in success now update email
                                    fauth.currentUser!!.updateEmail(newemailtxt)
                                        .addOnCompleteListener{ task ->
                                            if (task.isSuccessful) {


                                                Toast.makeText(applicationContext, "Email Successfuly changed", Toast.LENGTH_SHORT).show()


                                                df.update("email", newemailtxt).addOnSuccessListener {
                                                    Toast.makeText(applicationContext, "successfuly updated", Toast.LENGTH_SHORT).show()
                                                }
                                            }else{
                                                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                                            }
                                        }

                        }


                        }



                    }else{
                        if(passwordt.isNotEmpty()){
                            val credential = EmailAuthProvider.getCredential(emailTxt, passwordt);
                            val user : FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser()

                            user!!.reauthenticate(credential).addOnCompleteListener(this@EditProfile) { task ->
                                if (task.isSuccessful) {
                                    df.update("fName",fullmameTxt).addOnSuccessListener {
                                        Toast.makeText(applicationContext, "successfuly updated", Toast.LENGTH_SHORT).show()
                                    }
                                    df.update("phone",phoneTxt).addOnSuccessListener {
                                        Toast.makeText(applicationContext, "successfuly updated", Toast.LENGTH_SHORT).show()
                                    }


                                }

                            }

                        }else{

                            Toast.makeText(applicationContext, "Wrong Password", Toast.LENGTH_SHORT).show()
                        }





                    }





                }




                }


        })

    }
    private fun isEmailValid(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}