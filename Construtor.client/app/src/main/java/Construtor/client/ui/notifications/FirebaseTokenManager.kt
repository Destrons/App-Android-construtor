package com.Construtor.client.ui.notifications

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

object FirebaseTokenManager {

    private val db = FirebaseFirestore.getInstance()

    fun saveToken() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                db.collection("users").document(userId).update("fcmToken", token)
            }
        }
    }
}