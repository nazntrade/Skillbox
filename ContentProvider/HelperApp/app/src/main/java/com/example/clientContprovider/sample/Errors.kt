package com.example.clientContprovider.sample
//
//import android.os.Bundle
//import android.widget.Toast
//
//
//class Errors {
//    private fun publishFeedDialog(Link: String, Name: String, Photo: String, Descripcion: String) {
//        val params = Bundle()
//        params.putString("name", Name)
//        params.putString("caption", Caption)
//        params.putString("description", Descripcion)
//        params.putString("link", Link)
//        params.putString("picture", Photo)
//        val feedDialog: WebDialog = FeedDialogBuilder(
//            this@sEstablecimiento,
//            Session.getActiveSession(),
//            params
//        )
//            .setOnCompleteListener(object : OnCompleteListener() {
//                fun onComplete(
//                    values: Bundle,
//                    error: FacebookException?
//                ) {
//                    if (error == null) {
//                        val postId = values.getString("post_id")
//                        if (postId != null) {
//                            Toast.makeText(
//                                this@sEstablecimiento,
//                                "Posted story, id: $postId",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        } else {
//                            // User clicked the Cancel button
//                            Toast.makeText(
//                                this@sEstablecimiento.getApplicationContext(),
//                                "Publish cancelled",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    } else if (error is FacebookOperationCanceledException) {
//                        // User clicked the "x" button
//                        Toast.makeText(
//                            this@sEstablecimiento.getApplicationContext(),
//                            "Publish cancelled",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        // Generic, ex: network error
//                        Toast.makeText(
//                            this@sEstablecimiento.getApplicationContext(),
//                            "Error posting story",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            })
//            .build()
//        feedDialog.show()
//    }
//}