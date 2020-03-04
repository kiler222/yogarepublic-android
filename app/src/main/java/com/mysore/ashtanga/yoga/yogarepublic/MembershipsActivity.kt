package com.mysore.ashtanga.yoga.yogarepublic

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.android.synthetic.main.activity_memberships.*


class MembershipsActivity : AppCompatActivity() {
    external fun stringFromJNI():String
    val TAG = "PJ MembActivity"


    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: MembershipsAdapter
    private lateinit var membershipsList: ArrayList<Membership>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memberships)



//        val backImage = BitmapDrawable(resources, SharedDate.backgroudImage)

        backgroundImage.setImageBitmap(SharedDate.backgroudImage)

        window.setWindowAnimations(0)


        val radius = 20f

        val decorView: View = window.decorView


        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        val rootView = decorView.findViewById(android.R.id.content) as ViewGroup



        //Set drawable to draw in the beginning of each blurred frame (Optional).
        //Can be used in case your layout has a lot of transparent space and your content
        //gets kinda lost after after blur is applied.
        val windowBackground: Drawable = decorView.background // . getBackground()

        blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setHasFixedTransformationMatrix(true)


        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )





            adapter = MembershipsAdapter(SharedDate.membershipsForRecyclerView, this)
//            adapter.setHasStableIds(true)
            recyclerView.setItemViewCacheSize(20)
            recyclerView.adapter = adapter




    }


}
