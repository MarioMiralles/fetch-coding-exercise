package com.example.fetchrewardscodingexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FetchRewardsApp() {
    Column {
        TopAppBar(
            title = {
                Column {
                    Text(
                        "Fetch Rewards Coding Exercise",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Mario Miralles",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFfaa719),
                        fontWeight = FontWeight.Normal
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(0xFF300c38),
                titleContentColor = Color.White,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        ItemList()
        Spacer(modifier = Modifier.weight(1f)) // Push footer to the bottom
        FooterImage()
    }
}

@Composable
fun ItemList(viewModel: MainViewModel = viewModel()) {
    val items by viewModel.items.collectAsState()

    LazyColumn {
        items.groupBy { it.listId }.forEach { (listId, groupItems) ->
            item {
                ExpandableListGroup(listId, groupItems)
            }
        }
    }
}

@Composable
fun ExpandableListGroup(listId: Int, groupItems: List<Item>) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 9.dp),
        onClick = { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEDE7F6)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "List ID: $listId",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF300c38)
                )
                Text(
                    text = if (expanded) "▼" else "▶",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF300c38)
                )
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                groupItems.forEach { item ->
                    ItemCard(item)
                }
            }
        }
    }
}

@Composable
fun ItemCard(item: Item) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEFD5)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Name: ${item.name}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF300c38)
            )
            Text(
                text = "ID: ${item.id}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFfaa719)
            )
        }
    }
}

@Composable
fun FooterImage() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.fetch_rewards_logo),
            contentDescription = "Fetch Rewards Dog",
            modifier = Modifier
                .fillMaxWidth()
                .height(192.dp)
                .padding(16.dp),
            contentScale = ContentScale.Crop
        )
    }
}