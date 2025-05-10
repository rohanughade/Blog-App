package com.rohan.assignment.ui.uie.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.rohan.assignment.data.entity.BlogEntity
import com.rohan.assignment.ui.uie.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainScreen(viewModel: MainViewModel,onClick:(String)->Unit) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Blog App", style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        )

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFFFFF)
                )

            )
        }) {padding->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(color = Color(0xFFFFFFFF))){
            val blogdata = viewModel.postData.collectAsLazyPagingItems()

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item{
                    LazyRow {
                        items(blogdata.itemSnapshotList.items.take(6)){data->
                                blogItem(
                                    data,
                                    onClick = {
                                        onClick(it)
                                    }
                                )

                            }
                        }

                    }


                item{
                    Spacer(modifier = Modifier.height(8.dp))

                }

                items(blogdata.itemCount){
                            blogdata[it]?.let {data->
                                verticle(
                                    data,
                                    onClick = {
                                        onClick(it)
                                    }
                                )

                            }
                        }
                        blogdata.apply {
                            when{
                                loadState.refresh is LoadState.Loading->{
                                    item {
                                        Box(modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp)){
                                            CircularProgressIndicator(modifier = Modifier
                                                .padding(8.dp)
                                                .align(Alignment.Center))
                                        }
                                    }
                                }

                                loadState.append is LoadState.Loading->{
                                    item {
                                        Box(modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)){
                                            CircularProgressIndicator(modifier = Modifier
                                                .padding(8.dp)
                                                .align(Alignment.Center))
                                        }
                                    }
                                }

                                loadState.prepend is LoadState.Loading->{
                                    item {
                                        Box(modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)){
                                            CircularProgressIndicator(modifier = Modifier
                                                .padding(8.dp)
                                                .align(Alignment.Center))
                                        }
                                    }
                                }
                            }
                        }

                    }

                }
            }



}


@Composable
fun blogItem(data: BlogEntity,onClick: (String) -> Unit) {
    Card(elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
        .width(255.dp)
        .height(245.dp)

            .padding(8.dp)
        .clickable(
            onClick = {
                onClick(data.link)
            }
        ),

    )
    {
        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.imageUrl)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
            Text(text = data.title, color = Color.White,
                style = MaterialTheme.typography.titleMedium,

                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart))

        }
    }

}

@Composable
fun verticle(data: BlogEntity,onClick: (String) -> Unit) {
    Card (elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth().padding(6.dp)
        .clickable(
            onClick = {
                onClick(data.link)
            }
        )){
        Row(modifier = Modifier.fillMaxSize().background(color = Color(0xFFFAF9F6)).height(90.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.imageUrl)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(text = data.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier
                    .padding(8.dp))


        }
    }

}


