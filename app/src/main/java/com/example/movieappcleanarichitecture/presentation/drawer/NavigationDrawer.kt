package com.example.movieappcleanarichitecture.presentation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movieappcleanarichitecture.R
import com.example.movieappcleanarichitecture.common.extentions.advancedShadow
import com.example.movieappcleanarichitecture.common.extentions.coloredShadow
import com.example.movieappcleanarichitecture.common.extentions.customShadow
import com.example.movieappcleanarichitecture.navigation.MovieScreens
import com.example.movieappcleanarichitecture.ui.theme.AppColor

@Composable
fun NavigationDrawer(navHostController: NavHostController) {
    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(0.8f).customShadow(
            color = AppColor.vulcan.copy(alpha = 0.7f),
            blurRadius = 20.dp,
//            borderRadius = 20.dp,
            spread = 8.dp
        ),
        drawerContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.65f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(
                    end = 10.dp, start = 10.dp, top = 10.dp, bottom = 20.dp
                )
        )
        
        NavigationListItem(title = "Favorite Movie") {
            navHostController.navigate(MovieScreens.FAVORITE.name)
        }

        NavigationListItem(title = "About") {

        }

    }
}

@Composable
fun NavigationListItem(title: String, onPress: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onPress)
            .padding(bottom = 8.dp).customShadow(
                color = AppColor.vulcan.copy(alpha = 0.7f),
                blurRadius = 20.dp,
//            borderRadius = 20.dp,
                spread = 8.dp
            ),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
            text = title,
            style = MaterialTheme.typography.labelMedium.copy(color = Color.White)
        )
    }
}