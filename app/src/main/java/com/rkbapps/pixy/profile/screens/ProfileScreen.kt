package com.rkbapps.pixy.profile.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rkbapps.pixy.R

@Composable
fun ProfileScreen() {
    Column(Modifier.fillMaxSize()) {
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        ) {
//            RowItem(
//                Modifier
//                    .height(100.dp)
//                    .weight(1f)
//                    .padding(5.dp),
//                text = "Favourite",
//                icon = R.drawable.heart_colored,
//                iconTint = Color.Red
//            ){
//
//            }
//            RowItem(
//                Modifier
//                    .height(100.dp)
//                    .weight(1f)
//                    .padding(5.dp),
//                text = "Mode",
//                icon = R.drawable.moonnight,
//                iconTint = Color.Blue
//            ){
//
//            }
//            RowItem(
//                modifier = Modifier
//                    .height(100.dp)
//                    .weight(1f)
//                    .padding(5.dp),
//                text = "Favourite",
//                icon = R.drawable.heart_colored,
//                iconTint = Color.Red
//            ){
//
//            }
//        }
        LazyColumn(contentPadding = PaddingValues(5.dp)) {
            items(rowItemList) {
                ColumnItem(it)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowItem(modifier: Modifier, text: String, icon: Int, iconTint: Color, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick },
        colors = CardDefaults.cardColors(Color.White),
        modifier = modifier
    ) {
        Column {
            Text(
                text = text,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(8.dp)
            )
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier
                    .size(45.dp)
                    .align(Alignment.End)
                    .padding(top = 5.dp, end = 1.dp)
            )
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnItem(item: ColumnItemModel) {
    Card(
        onClick = {

        },
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(item.icon),
                contentDescription = item.text,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
            )
            Text(text = item.text, modifier = Modifier.weight(1f))
        }
    }


}

data class ColumnItemModel(val icon: Int, val text: String)

val rowItemList = listOf(
    ColumnItemModel(R.drawable.settingsaccountmore, "How to set wallpaper"),
    ColumnItemModel(icon = R.drawable.share, text = "Tell a Friend"),
    ColumnItemModel(icon = R.drawable.chatmessage, text = "Feedback"),
    ColumnItemModel(icon = R.drawable.helpquestion, text = "Help Center"),
    ColumnItemModel(icon = R.drawable.info, text = "About Us"),
    ColumnItemModel(icon = R.drawable.trashdeletebin, text = "Clear Caches")
)


@Preview
@Composable
fun DefaultPreview() {
//    RowItem(
//
//    )
}