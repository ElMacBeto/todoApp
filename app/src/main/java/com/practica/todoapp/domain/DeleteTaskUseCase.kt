package com.practica.todoapp.domain

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.ContentValues
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.Transformation
import com.practica.todoapp.data.datasource.database.entity.TaskEntity
import com.practica.todoapp.data.repository.LocalRepository
import com.practica.todoapp.di.component.DaggerRepositoryComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteTaskUseCase {

    @Inject
    lateinit var repository: LocalRepository

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    //    operator fun invoke(task:TaskEntity, item: View, function:()->Unit){
    suspend operator fun invoke(task: TaskEntity, function: () -> Unit) {
        repository.deleteTask(task)
        function()
//        animateHeight(item, item.height, task, function)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun animateHeight(v: View, height: Int, task: TaskEntity, function: () -> Unit) {
        val initialHeight = v.measuredHeight
        val duration = 400
        val interpolator: Interpolator = AccelerateInterpolator(2F)

        // I have to set the same height before the animation because there is a glitch
        // in the beginning of the animation
        v.layoutParams.height = initialHeight
        v.requestLayout()
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                Log.d(ContentValues.TAG, "InterpolatedTime: $interpolatedTime")
                Log.d(
                    ContentValues.TAG,
                    "Collapsing height: " + (initialHeight - (height * interpolatedTime).toInt())
                )
                v.layoutParams.height = initialHeight - (height * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.duration = duration.toLong()
        a.interpolator = interpolator
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                GlobalScope.launch {
                    repository.deleteTask(task)
                    function()
                }
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
        v.startAnimation(a)
    }

//    vval animator = ObjectAnimator.ofFloat(item, View.TRANSLATION_X, 0f, 1200f)
//    animator.duration = 300
//
//    val enterAnimator =  ObjectAnimator.ofFloat(item, View.TRANSLATION_X, 1200f, 0f).apply {
//        duration = 50
//        addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationStart(animation: Animator?) {}
//            override fun onAnimationEnd(animation: Animator?) {
//                GlobalScope.launch {
//                    repository.deleteTask(task)
//                    function()
//                }
//            }
//        })
//    }
//
//    animator.addListener(object : AnimatorListenerAdapter() {
//        override fun onAnimationStart(animation: Animator?) {}
//        override fun onAnimationEnd(animation: Animator?) {
//            item.visibility = View.GONE
//            enterAnimator.start()
//        }
//    })
//    animator.start()


}