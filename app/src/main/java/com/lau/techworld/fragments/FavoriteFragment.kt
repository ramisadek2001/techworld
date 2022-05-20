package com.lau.techworld.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.lau.techworld.MyAdapter
import com.lau.techworld.Product
import com.lau.techworld.Product_Page
import com.lau.techworld.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var productRecyclerView: RecyclerView
private lateinit var productArraylist : ArrayList<Product>
val fauth = FirebaseAuth.getInstance()
val fstore = FirebaseFirestore.getInstance()
val userID = fauth.currentUser!!.uid

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {


    val fauth = FirebaseAuth.getInstance()
    val fstore = FirebaseFirestore.getInstance()
    val userID = fauth.currentUser!!.uid

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View =
            inflater.inflate(com.lau.techworld.R.layout.fragment_favorite, container, false)

        val image1 = view.findViewById<View>(R.id.sal)
        val image2 = view.findViewById<View>(R.id.no)

        productRecyclerView = view.findViewById<View>(com.lau.techworld.R.id.recyclerfav) as RecyclerView

        productRecyclerView.layoutManager = GridLayoutManager(context, 2)
        productRecyclerView.setHasFixedSize(true)


//
        productArraylist = arrayListOf<Product>()

        fstore.collection("Favorites").document(userID).collection("CurrentUser").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {

                for (documentSnapshot : DocumentSnapshot in task.getResult().documents ){
                    if (documentSnapshot.getString("favorite").toString() == "true"){
                        val product : Product = documentSnapshot.toObject(Product :: class.java)!!

                        productArraylist.add(product)

                        var adapter = MyAdapter(productArraylist)
                        productRecyclerView.adapter = adapter
                        image1.isVisible = false
                        image2.isVisible = false
                        productRecyclerView.isVisible = true
                        adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                            override fun OnItemClick(position: Int) {
                                val intent = Intent(context,Product_Page::class.java)
                                intent.putExtra("title", productArraylist[position].title)
                                intent.putExtra("price", productArraylist[position].price.toString())
                                intent.putExtra("image", productArraylist[position].image)
                                intent.putExtra("desc", productArraylist[position].desc)

                                startActivity(intent)
                            }

                        })
                    }


                }

            }


        }

        if (productArraylist.isEmpty()){
            productRecyclerView.isVisible = false

            image1.isVisible = true
            image2.isVisible = true
        }




        return view

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
