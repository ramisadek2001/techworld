package com.lau.techworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lau.techworld.fragments.CartFragment
import com.lau.techworld.fragments.FavoriteFragment
import com.lau.techworld.fragments.HomeFragment
import com.lau.techworld.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val HomeFragment= com.lau.techworld.fragments.HomeFragment()
    private val FavoriteFragment = com.lau.techworld.fragments.FavoriteFragment()
    private val ProfileFragment = com.lau.techworld.fragments.ProfileFragment()
    private val CartFragment = com.lau.techworld.fragments.CartFragment()
    val fauth : FirebaseAuth = FirebaseAuth.getInstance()
    val fStore = Firebase.firestore
    lateinit var userID : String
    private var fragmentTransaction: FragmentTransaction? = null
    var someFragment: HomeFragment = HomeFragment()
//    private val bottomnav = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)
//step 1: create item in recycler view
//step 2: create class to add items
//step3: attach and create  adapter
//step 4: attach adapter to recycler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ReplaceFragment(HomeFragment)


        bottom_navigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> ReplaceFragment(HomeFragment)
                R.id.ic_favorite -> ReplaceFragment(FavoriteFragment)
                R.id.ic_cart -> ReplaceFragment(CartFragment)
                R.id.ic_profile -> ReplaceFragment(ProfileFragment)

            }
            true
        }


//    val cartB : Button = findViewById(R.id.cartButton)

//    cartB.setOnClickListener(object : View.OnClickListener{
//        override fun onClick(p0: View?) {
//            userID = fauth.currentUser!!.uid
//            val df : DocumentReference = fStore.collection("users").document(userID).collection("ProductsInCart").document("Products")
//
//            val products = hashMapOf<>()
//
//
//        }
//
//
//    })


    }

    private fun ReplaceFragment(fragment: Fragment){

        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}