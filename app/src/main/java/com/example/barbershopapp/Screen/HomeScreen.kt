package com.example.barbershopapp.Screen

import android.app.Service
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Star
import com.example.barbershopapp.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barbershopapp.components.AppToolbar
import com.example.barbershopapp.components.AutoUpdatingRatingBar
import com.example.barbershopapp.components.CustomTopBar
import com.example.barbershopapp.components.HeadingTextComponent

import com.example.barbershopapp.components.SppokyAppBottomNavigation
import com.example.barbershopapp.components.discount
import com.example.barbershopapp.components.ratingbody
import com.example.barbershopapp.data.firebase.AuthRepo
import com.example.barbershopapp.data.firebase.AuthViewModel
import com.example.barbershopapp.data.firebase.AuthViewModelFactory
import com.example.barbershopapp.data.firebase.CityRepo
import com.example.barbershopapp.data.firebase.StylistRepo
import com.example.barbershopapp.data.home.BottomNavItem
import com.example.barbershopapp.data.user.UserViewModel
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.example.barbershopapp.ui.theme.fontFamily1
import com.example.barbershopapp.ui.theme.fontFamily2

@Composable
fun ServiceColumn(
    imageResource : Int,
    contentDescription : String,
    serviceName : String,
    modifier : Modifier = Modifier
){
    Column (
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.White)
            .shadow(
                3.dp,
                RoundedCornerShape(10.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = contentDescription,
            modifier = modifier.size(130.dp)
        )
        Text(text = serviceName, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Blue)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServiceFlow() {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        ServiceColumn(imageResource = R.drawable.cattoc, contentDescription = "Cắt tóc", serviceName = "Cắt tóc")
        ServiceColumn(imageResource = R.drawable.uontoc, contentDescription = "Uốn tóc", serviceName = "Uốn tóc")
        ServiceColumn(imageResource = R.drawable.duoitoc, contentDescription = "Duỗi tóc", serviceName = "Duỗi tóc")
        ServiceColumn(imageResource = R.drawable.goidau, contentDescription = "Gội đầu", serviceName = "Gội đầu")
    }
}
@Composable
fun BodyContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        HeadingTextComponent(value = stringResource(id = R.string.rating))
        ratingbody()
        Spacer(modifier = Modifier.height(16.dp))
        HeadingTextComponent(value = stringResource(id = R.string.coupon))
        discount()
        Spacer(modifier = Modifier.height(16.dp))
        HeadingTextComponent(value = stringResource(id = R.string.service))
        ServiceFlow()
    }
}

@Composable
fun UserHomeScreen() {
    val authRepo = AuthRepo()
    val cityRepo = CityRepo()
    val stylistRepo = StylistRepo()
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(authRepo, cityRepo, stylistRepo)
    )

    val user by authViewModel.user.collectAsState()

    val username = user?.name ?: "GUEST"

    Scaffold(
        bottomBar = {
            SppokyAppBottomNavigation()
        },
        topBar = {
            CustomTopBar(username = username)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            item {
                BodyContent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BodyPreview(){
    BodyContent()
}