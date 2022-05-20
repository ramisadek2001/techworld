package com.lau.techworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.lau.techworld.fragments.Fname
import com.lau.techworld.fragments.Pemail
import com.lau.techworld.fragments.Pnumber

class checkout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        val back = findViewById<ImageView>(R.id.leftIco)

        back.setImageResource(R.drawable.ic_baseline_keyboard_arrow_left_24)
        back.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext,MainActivity::class.java))
            }

        })
        val fauth = FirebaseAuth.getInstance()
        val fstore = FirebaseFirestore.getInstance()
        val userID = fauth.currentUser!!.uid
        val chang = findViewById<AppCompatButton>(R.id.change)
        chang.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext,Address_Page::class.java))
            }

        })
        val name = findViewById<TextView>(R.id.name) as TextView
        val number = findViewById<TextView>(R.id.phonenum) as TextView
        val address = findViewById<TextView>(R.id.location) as TextView
        val df : DocumentReference = fstore.collection("users").document(userID)


        df.addSnapshotListener(MetadataChanges.INCLUDE){ snapshot, e ->

            name.setText(snapshot!!.getString("fName"))
            number.setText(snapshot!!.getString("phone"))
            address.setText(snapshot!!.getString("addtitle"))

        }
        val conf = findViewById<AppCompatButton>(R.id.confirm)

        conf.setOnClickListener(object : View.OnClickListener{

            override fun onClick(p0: View?) {
                fstore.collection("Favorites").document(userID).collection("CurrentUser").get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val order = hashMapOf<String, String>()
                        for (documentSnapshot: DocumentSnapshot in task.getResult().documents) {
                            if (documentSnapshot.getString("inCart").toString() == "true") {


                                order.put("userID",userID)
                                order.put("order",documentSnapshot.getString("title")+" quantity: "+documentSnapshot.getString("quantity")+" price: "+documentSnapshot.getString("price"))
                                order.put("delivered","false")

                                val dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://techworld-69698-default-rtdb.firebaseio.com")
                                dbref.child("orders").child(userID).child(documentSnapshot.getString("price").toString()).setValue(order)
                                val df : DocumentReference = fstore.collection("orders").document(userID).collection("current user").document(documentSnapshot.getString("price").toString())
                                df.set(order)
                            }

                        }

                        Toast.makeText(applicationContext, "Order has been processed", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                            }


            }

        })



    }
}