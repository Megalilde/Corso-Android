package com.example.mywishlistapp_compose.widgets

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mywishlistapp_compose.data.Wish
import com.example.mywishlistapp_compose.navigation.Screen
import com.example.mywishlistapp_compose.viewmodel.WishViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
){
    val context = LocalContext.current

    Scaffold (
        topBar = {
            AppBarView(title = "WishList", onBackNavClicked = {
            Toast.makeText(context, "Button Clicked",  Toast.LENGTH_LONG).show()
        })
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                shape = CircleShape,
                containerColor = Color.Black,
                contentColor = Color.White,
                onClick = {
                    Toast.makeText(context, "FabButton Clicked",  Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.AddScreen.route + "/0L")
                }

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        // Si usa questo invece dell'observe.
        val wishList = viewModel.getAllWishes.collectAsState(initial = listOf())
        LazyColumn (modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            // wish -> wish.id molto importante
            items(wishList.value, key = {wish -> wish.id }){
                wish ->
                val dismissState = rememberSwipeToDismissBoxState()
                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        Row(Modifier.fillMaxSize()){
                            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart){
                                viewModel.deleteWish(wish)
                            }
                        }
                    },
                    enableDismissFromStartToEnd = true,
                    enableDismissFromEndToStart = true,
                    content = {
                        WishItem(wish = wish){
                            val id = wish.id
                            navController.navigate(Screen.AddScreen.route + "/$id")
                            }
                        }
                    )

            }
        }
    }
}


@Composable
fun WishItem(wish: Wish, onClick: () -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        .clickable {
            onClick()
        },
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
        ){

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
        }
    }
}