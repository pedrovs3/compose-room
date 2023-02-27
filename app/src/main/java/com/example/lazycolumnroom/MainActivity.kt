package com.example.lazycolumnroom

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lazycolumnroom.dao.repository.ProductRepository
import com.example.lazycolumnroom.model.Product
import com.example.lazycolumnroom.ui.theme.LazyColumnRoomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnRoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var nameState by remember{
        mutableStateOf("")
    }

    var priceState by remember{
        mutableStateOf("")
    }

    var productsState by remember{
        mutableStateOf(listOf<Product>())
    }

    var productRepository = ProductRepository(LocalContext.current)
    val context = LocalContext.current
    productsState = productRepository.getProductsList()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Listas com Jetpack Compose")

        OutlinedTextField(
            value = nameState,
            onValueChange ={ nameState = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Titulo do produto.") }
        )

        OutlinedTextField(
            value = priceState,
            onValueChange ={priceState = it},
            modifier = Modifier.fillMaxWidth(),
            label = {Text(text = "Preço do produto.")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(

            onClick = {
                      val p = Product(
                          productName = nameState,
                          productPrice = priceState.toDouble()
                          )
                val newId = productRepository.save(p)
                productsState = productRepository.getProductsList()
                Toast.makeText(context, "$newId", Toast.LENGTH_SHORT).show()

            }
        ) {
            Text(text = "button")

        }

        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(productsState) {
                ProductCard(it)
            }


        }
    }
}

@Composable
fun ProductCard(it: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color(103, 58, 183, 255)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "${it.id} - ${it.productName}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = it.productDescription,
                fontSize = 12.sp
            )
            Text(text = "$${it.productPrice}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LazyColumnRoomTheme {
        Greeting("Android")
    }
}