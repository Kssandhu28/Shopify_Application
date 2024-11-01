package com.example.shopifyapplication.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.shopifyapplication.Adapter.SliderAdapter
import com.example.shopifyapplication.Domain.SliderItems
import com.example.shopifyapplication.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebase()
        initBanner()
    }

    private fun initFirebase() {
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("Banner")
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE // Set visibility to VISIBLE
        val bannerList = ArrayList<SliderItems>()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (bannerSnapshot in snapshot.children) {
                        val bannerItem = bannerSnapshot.getValue(SliderItems::class.java)
                        if (bannerItem != null) {
                            bannerList.add(bannerItem)
                        }
                    }
                }
                binding.progressBarBanner.visibility = View.GONE // Hide progress bar after loading
            }

            override fun onCancelled(@NonNull DatabaseError) {
            }
        });
    }
    private fun banners(items: ArrayList<SliderItems>) {
        binding.viewpagerSlider.adapter = SliderAdapter(items, binding.viewpagerSlider)
        binding.viewpagerSlider.clipToPadding = false
        binding.viewpagerSlider.clipChildren = false
        binding.viewpagerSlider.offscreenPageLimit = 3
        binding.viewpagerSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))

        binding.viewpagerSlider.setPageTransformer(compositePageTransformer)
    }

}
