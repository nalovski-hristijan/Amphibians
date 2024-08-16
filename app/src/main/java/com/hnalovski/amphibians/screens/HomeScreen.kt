package com.hnalovski.amphibians.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.hnalovski.amphibians.components.AmphibianAppBar
import com.hnalovski.amphibians.model.Amphibian

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmphibianApp(viewModel: HomeScreenViewModel = hiltViewModel()) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val ampData = viewModel.amphibianData.value

    val context = LocalContext.current


    when {
        ampData.loading == true -> {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
        ampData.data != null -> {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = { AmphibianAppBar(title = "Amphibians", scrollBehavior = scrollBehavior) }
            ) { innerPadding ->
                Surface(modifier = Modifier.padding(innerPadding), color = Color(0xFFfcfdf6)) {
                    AmphibianList(amphibian = ampData.data!!)
                }
            }
        }
        else -> {
            ErrorScreen(retryAction = {
                viewModel.getAmphibian()
                Toast.makeText(context, "Retrying...", Toast.LENGTH_SHORT).show()
            })
        }
    }
}

@Preview
@Composable
fun ErrorScreen(retryAction: () -> Unit = {}) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Error loading items...", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                modifier = Modifier.width(200.dp),
                onClick = { retryAction.invoke() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text(text = "Retry")
            }
        }

    }
}



@Composable
fun AmphibianList(amphibian: List<Amphibian>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = amphibian) { item ->
            FrogRow(amphibian = item)
        }
    }
}


@Composable
fun FrogRow(
    amphibian: Amphibian
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, start = 15.dp, end = 15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFdee5d8))
    ) {
        Column {
            Text(
                text = amphibian.name + " (${amphibian.type})",
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = rememberAsyncImagePainter(model = amphibian.img_src),
                contentDescription = "amphibian image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = amphibian.description,
                modifier = Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    top = 20.dp,
                    bottom = 20.dp
                ),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}




