package com.cs4520.assignment5

import android.util.Log
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.compose.material.OutlinedTextField

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController

@Composable
fun ItemsPage(navHostController: NavHostController, viewModel: ProductListViewModel) {
    val isLoading by viewModel.isLoading.collectAsState();
    val error by viewModel.errors.collectAsState();
    val productsList by viewModel.productList.collectAsState();

    if (isLoading) {
        LoadingScreen()
    }
    else if (error != null) {
        ErrorScreen(error?:"")
    }
    else {
        ItemsList(productsList)
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color.Blue,
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
fun ErrorScreen(error: String) {
    Text(
        text = error,
        color = Color.Black,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(16.dp)
    )
}


@Composable
fun ItemsList(products: List<Product>) {
    LazyColumn {
        items(products) { product ->
            Item(product)
        }
    }
}

@Composable
fun Item(product: Product) {
    val nameText = product.name;
    val nameTextVisibility = product.name != null;

    val priceText = "$ " + product.price.toString();
    val priceTextVisibility = product.price != null;

    val expText = product.expiryDate;
    val expTextVisibility = product.expiryDate != null;

    val backgroundColor = when (product.type) {
        "Equipment" -> R.color.light_red
        "Food" -> R.color.light_yellow
        else -> R.color.light_red
    }

    val imageResource = when (product.type) {
        "Equipment" -> R.drawable.tools
        "Food" -> R.drawable.food
        else -> R.drawable.food
    }

    val color = ContextCompat.getColor(LocalContext.current, backgroundColor)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(color)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            if (nameTextVisibility) {
                Text(
                    text = nameText?:"",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (priceTextVisibility) {
                Text(
                    text = priceText?:"",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (expTextVisibility) {
                Text(
                    text = expText?:"",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

}
//@Composable
//fun Conversation(messages: List<Message>) {
//    LazyColumn {
//        items(messages) { message ->
//            MessageCard(message)
//        }
//    }
//}
//@Preview
//@Composable
//fun PreviewConversation() {
//    ComposeTutorialTheme {
//        Conversation(SampleData.conversationSample)
//    }
//}
