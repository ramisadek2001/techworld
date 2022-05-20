package com.lau.techworld.fragments


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.firebase.database.*

import android.R
import android.content.Intent
import android.widget.Button
import android.widget.SearchView


import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lau.techworld.All_Items
import kotlinx.android.synthetic.main.fragment_home.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"






/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {



    private val CamerasFragment= com.lau.techworld.fragments.CamerasFragment()
    private val LaptopsFragment = com.lau.techworld.fragments.LaptopsFragment()
    private val MobileFragment = com.lau.techworld.fragments.MobileFragment()
    private val TabletsFragment = com.lau.techworld.fragments.TabletsFragment()
    val dbref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://techworld-69698-default-rtdb.firebaseio.com/products/mobile")
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var bottomnav: BottomNavigationView
    private lateinit var button: Button
    public lateinit var search : SearchView

override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    val view: View = inflater.inflate(com.lau.techworld.R.layout.fragment_home, container, false)
   bottomnav = view.findViewById<View>(com.lau.techworld.R.id.bottomNavigationView) as BottomNavigationView
    ReplaceFragment(CamerasFragment)
        bottomnav.setOnItemSelectedListener {
            when(it.itemId){
                com.lau.techworld.R.id.cameras -> ReplaceFragment(CamerasFragment)
                com.lau.techworld.R.id.tablets -> ReplaceFragment(TabletsFragment)
                com.lau.techworld.R.id.mobile -> ReplaceFragment(MobileFragment)
                com.lau.techworld.R.id.laptops -> ReplaceFragment(LaptopsFragment)

            }
            true
        }
    button = view.findViewById<View>(com.lau.techworld.R.id.button2) as Button

    val search = view.findViewById<View>(com.lau.techworld.R.id.search_2)
    search.setOnClickListener(object : View.OnClickListener{
        override fun onClick(p0: View?) {
            val intent = Intent(activity, All_Items::class.java)
            startActivity(intent)
        }

    })

    button.setOnClickListener(object : View.OnClickListener{
        override fun onClick(p0: View?) {

            val intent = Intent(activity, All_Items::class.java)
            startActivity(intent)

        }

    })



    return view
}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }






    }
   private fun ReplaceFragment(fragment: Fragment){

        if (fragment != null){
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(com.lau.techworld.R.id.fragment_cont, fragment)
            transaction.commit()
        }
    }

//    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(itemView, savedInstanceState)
//        productRecyclerView.apply {
//            // set a LinearLayoutManager to handle Android
//            // RecyclerView behavior
//            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false)
//            // set the custom adapter to the RecyclerView
//            adapter = MyAdapter(productArraylist)
//        }
//    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}