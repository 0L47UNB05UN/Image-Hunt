package com.example.imagehunt.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imagehunt.R
import java.util.Calendar

@Composable
fun About(modifier: Modifier = Modifier){
    Box(
        contentAlignment = Alignment.Center,
    ){
        Image(
            painter = painterResource(R.drawable.app_icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.1f,
            modifier = modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.app_icon),
                contentDescription = null,
            )
            Text(
                text = stringResource(R.string.business), fontSize = 22.sp,
                color = Color(0f, 0.4f, 0f),
                modifier = modifier.padding(bottom = 8.dp)
            )
            Text(text = stringResource(R.string.name), fontSize = 18.sp)
            Text(text = stringResource(R.string.contact), fontSize = 14.sp)
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info, contentDescription = null,
                    modifier = modifier.padding(12.dp)
                )
                Text(
                    stringResource(R.string.app_name), fontSize = 16.sp,
                    modifier = modifier.padding(end = 5.dp)
                )
                Text(
                    text = Calendar.getInstance().get(Calendar.YEAR).toString(),
                    fontSize = 16.sp
                )
            }
        }
    }
}
