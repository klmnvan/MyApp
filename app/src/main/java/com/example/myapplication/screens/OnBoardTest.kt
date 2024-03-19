package com.example.myapplication.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityOnBoardTestBinding
import com.example.myapplication.models.OnBoard
import com.example.myapplication.objects.PrefManager
import java.util.LinkedList
import java.util.Queue

class OnBoardTest : AppCompatActivity(), AnimationListener {
    private lateinit var binding: ActivityOnBoardTestBinding
    private var queue: Queue<OnBoard> = LinkedList()
    lateinit var animation1: Animation
    lateinit var animation2: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PrefManager.initPref(this@OnBoardTest)
        if(PrefManager.queueIsCreate){
            createQueue()
        } else {
            createQueue()
        }
        enterQueue(queue.poll())
        pressButtons()
        animation1 = AnimationUtils.loadAnimation(this@OnBoardTest,R.anim.alpha_in)
        animation2 = AnimationUtils.loadAnimation(this@OnBoardTest, R.anim.alpha_out)
        animation1.setAnimationListener(this)
    }

    fun pressButtons(){
        with(binding) {
            bSkip.setOnClickListener {
                startActivity(Intent(this@OnBoardTest, MainActivity::class.java))
                finish()
            }
            bNext.setOnClickListener {
                image.startAnimation(animation1)
                tTit.startAnimation(animation1)
                tDes.startAnimation(animation1)
            }
        }
    }

    fun createQueue(){
        queue = LinkedList(
            listOf(
                OnBoard(R.drawable.onb1, "Quick Delivery At Your Doorstep", "Enjoy quick pick-up and delivery to your destination", 1),
                OnBoard(R.drawable.onb2, "Flexible Payment", "Different modes of payment either before and after delivery without stress", 2),
                OnBoard(R.drawable.onb3, "Real-time Tracking", "Track your packages/items from the comfort of your home till final destination", 3),
            )
        )
        PrefManager.queueIsCreate = true
    }

    fun enterQueue(model: OnBoard){
        with(binding){
            tTit.text = model.tittle
            tDes.text = model.d
            image.background = getDrawable(model.image)
            if(queue.size == 0) {
                showHints()
            }
            initPoint(model.point)
        }
    }

    fun showHints(){
        with(binding){
            LLHints.visibility = View.VISIBLE
            bSignUp.visibility = View.VISIBLE
            bNext.visibility = View.INVISIBLE
            bSkip.visibility = View.INVISIBLE
        }
    }

    private fun initPoint(count: Int) {
        with(binding) {
            when(count) {
                1 ->  {
                    p1.background = getDrawable(R.drawable.oval_blue)
                    p2.background = getDrawable(R.drawable.oval_blue_corners)
                    p3.background = getDrawable(R.drawable.oval_blue_corners)
                }
                2 -> {
                    p1.background = getDrawable(R.drawable.oval_blue_corners)
                    p2.background = getDrawable(R.drawable.oval_blue)
                    p3.background = getDrawable(R.drawable.oval_blue_corners)
                }
                3 -> {
                    p1.background = getDrawable(R.drawable.oval_blue_corners)
                    p2.background = getDrawable(R.drawable.oval_blue_corners)
                    p3.background = getDrawable(R.drawable.oval_blue)
                }
            }
        }

    }

    override fun onAnimationStart(p0: Animation?) {

    }

    override fun onAnimationEnd(p0: Animation?) {
        with(binding){
            enterQueue(queue.poll())
            image.startAnimation(animation2)
            tTit.startAnimation(animation2)
            tDes.startAnimation(animation2)

        }

    }

    override fun onAnimationRepeat(p0: Animation?) {

    }
}