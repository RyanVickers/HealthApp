package com.example.healthapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class GoalListViewModel : ViewModel() {

    private val goals = MutableLiveData<List<Goal>>()

    init {
        loadGoals()
    }

    /*get goals*/
    fun getGoals(): LiveData<List<Goal>> {
        return goals
    }

    /*Load goals from database */
    private fun loadGoals() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance().collection("goals").whereEqualTo("userId", userId)


        db.addSnapshotListener { documents, exception ->
            Log.i("DB_RESPONSE", "# of elements returned ${documents?.size()}")
            if (exception != null) {
                Log.w("DB_RESPONSE", "Listen failed", exception)
                return@addSnapshotListener
            }

            val goalList = ArrayList<Goal>()
            documents?.let {
                for (document in documents) {
                    val goal = document.toObject(Goal::class.java)
                    goalList.add(goal)
                }
                goals.value = goalList
            }
        }
    }
}