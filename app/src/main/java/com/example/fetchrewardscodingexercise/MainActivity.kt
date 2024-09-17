package com.example.fetchrewardscodingexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FetchRewardsApp()
                }
            }
        }
    }
}

@Composable
fun FetchRewardsApp() {
    Column {
        TopAppBar(
            title = { Text("Fetch Rewards Coding Exercise") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        )
        ItemList()
    }
}

@Composable
fun ItemList(viewModel: MainViewModel = viewModel()) {
    val items by viewModel.items.collectAsState()

    LazyColumn {
        items.groupBy { it.listId }.forEach { (listId, groupItems) ->
            item {
                Text(
                    text = "List ID: $listId",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(groupItems) { item ->
                ItemCard(item)
            }
        }
    }
}

@Composable
fun ItemCard(item: Item) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${item.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "ID: ${item.id}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}