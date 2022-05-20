package com.lau.techworld

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.invoke.MethodHandleInfo.toString
import java.util.Arrays.toString


private lateinit var orderArraylist : ArrayList<order>
private lateinit var orderRecyclerView: RecyclerView
val fauth = FirebaseAuth.getInstance()
val fstore = FirebaseFirestore.getInstance()
val userID = fauth.currentUser!!.uid

class Orders : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        val back = findViewById<ImageView>(R.id.leftIco)
        val image1 = findViewById<ImageView>(R.id.no)


        back.setImageResource(R.drawable.ic_baseline_keyboard_arrow_left_24)
        back.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(applicationContext,MainActivity::class.java))
            }

        })


        orderRecyclerView = findViewById<RecyclerView>(com.lau.techworld.R.id.recyclerorders)

        orderRecyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
        orderRecyclerView.setHasFixedSize(true)

        orderArraylist = arrayListOf<order>()



      val dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://techworld-69698-default-rtdb.firebaseio.com/orders/"+ userID)
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (productSnapshot in snapshot.children) {


                        val order = productSnapshot.getValue(order::class.java)

                        orderArraylist.add(order!!)
                        orderRecyclerView.isVisible = true

                        image1.isVisible = false

                    }
                    orderRecyclerView.adapter  = ordersAdapter(orderArraylist)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        Log.e(TAG, "onCreate: "+ orderArraylist, )


        if (orderArraylist.isEmpty()){
            orderRecyclerView.isVisible = false

            image1.isVisible = true

        }
    }
}
public fun getorderData(image1:ImageView) {

}