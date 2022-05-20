package com.lau.techworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_address_page.*

class Address_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_page)

        val addtitle = findViewById<EditText>(R.id.addTitle)
        val street = findViewById<EditText>(R.id.street)
        val building = findViewById<EditText>(R.id.building)
        val apartment = findViewById<EditText>(R.id.apartment)
        val inst = findViewById<EditText>(R.id.instructions)


        val fauth : FirebaseAuth = FirebaseAuth.getInstance()
        val fStore = Firebase.firestore
        val userID = fauth.currentUser!!.uid
        val back = findViewById<ImageView>(R.id.leftIco)

        back.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext,MainActivity::class.java))
            }

        })


        val df : DocumentReference = fStore.collection("users").document(userID)

        val conf = findViewById<AppCompatButton>(R.id.confirm)
        df.addSnapshotListener(MetadataChanges.INCLUDE){ snapshot, e ->

            addtitle.setText(snapshot?.getString("addtitle"))
            street.setText(snapshot?.getString("street"))
            building.setText(snapshot?.getString("building"))
            apartment.setText(snapshot?.getString("apartment"))
            inst.setText(snapshot?.getString("instructions"))
        }





            conf.setOnClickListener(object : View.OnClickListener{

                override fun onClick(p0: View?) {



                    val addtitletxt = addTitle.text.toString()
                    val streettxt = street.text.toString()
                    val buildingtxt = building.text.toString()
                    val apartmenttxt = apartment.text.toString()
                    val insttxt = inst.text.toString()
                    if (addtitletxt.isEmpty() || streettxt.isEmpty() || buildingtxt.isEmpty() || apartmenttxt.isEmpty()){
                        Toast.makeText(applicationContext,"Please fill all fields", Toast.LENGTH_SHORT).show()
                    }else{


                        val df : DocumentReference = fStore.collection("users").document(userID)


                        df.update("addtitle",addtitletxt)
                        df.update("street", streettxt)
                        df.update("building",buildingtxt)
                        df.update("apartment",apartmenttxt)
                        df.update("instructions",insttxt)
                        startActivity(Intent(this@Address_Page,MainActivity::class.java))

                    }





                    }

            })





    }
}