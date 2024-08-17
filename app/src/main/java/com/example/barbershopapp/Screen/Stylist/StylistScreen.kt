package com.example.barbershopapp.Screen.Stylist

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.barbershopapp.data.stylist.Stylist
import com.example.barbershopapp.components.HeadingTextComponent
import com.example.barbershopapp.components.StylistItemComponent
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun StylistScreen() {
    val firestore = FirebaseFirestore.getInstance()
    var stylistList by remember { mutableStateOf<List<Stylist>>(emptyList()) }

    Column(modifier = Modifier.fillMaxSize()) {
        HeadingTextComponent(value = "DANH SÁCH THỢ CẮT TÓC")
        Spacer(modifier = Modifier.height(20.dp))

        LaunchedEffect(Unit) {
            firestore.collection("stylist")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.e("StylistScreen", "Error fetching stylists", error)
                        return@addSnapshotListener
                    }
                    snapshot?.let {
                        val stylists = it.toObjects(Stylist::class.java)
                        stylistList = stylists
                    }
                }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(stylistList) { stylist ->
                StylistItemComponent(stylist = stylist)
            }
        }
    }
}
