package com.example.kotlin_compose_practice_superheroes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_compose_practice_superheroes.model.Hero
import com.example.kotlin_compose_practice_superheroes.model.HeroesDataSource.heroes
import com.example.kotlin_compose_practice_superheroes.ui.theme.KotlincomposepracticesuperheroesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlincomposepracticesuperheroesTheme {
                HeroApp()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HeroApp() {

    val visibleState = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    Scaffold(
        topBar = {
            HeroesTopAppBar()
        }
    ) {

        AnimatedVisibility(
            visibleState = visibleState,
            enter = fadeIn(
                animationSpec = spring(dampingRatio = DampingRatioLowBouncy)
            ),
            exit = fadeOut()
        ) {
            LazyColumn(
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                itemsIndexed(heroes) { index, hero ->
                    HeroItem(
                        hero = hero,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .animateEnterExit(
                                enter = slideInVertically (
                                    animationSpec = spring(
                                        stiffness = StiffnessLow,
                                        dampingRatio = DampingRatioLowBouncy
                                    ),
                                    initialOffsetY = { it * (index + 1) }
//                                            enter = slideInHorizontally(
//                                    animationSpec = spring(
//                                        stiffness = StiffnessLow,
//                                        dampingRatio = DampingRatioLowBouncy
//                                    ),
//                                    initialOffsetX = { it * (index + 1) }
                                )
                            )

                    )
                }
            }

        }

    }
}

@Composable
fun HeroesTopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h1
        )
    }

}

@Composable
fun HeroItem(hero: Hero, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
                .padding(16.dp)
        ) {
//            HeroInformation(hero.nameRes, hero.descriptionRes, Modifier.weight(1f))
//            Spacer(Modifier.width(16.dp))
//            HeroIcon(heroIcon = hero.imageRes)

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = hero.nameRes),
                    style = MaterialTheme.typography.h3
                )
                Text(
                    text = stringResource(id = hero.descriptionRes),
                    style = MaterialTheme.typography.body1
                )
            }
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource(hero.imageRes),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth
                )
            }
        }

    }

}

@Composable
fun HeroIcon(
    @DrawableRes heroIcon: Int, modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(8)),
        contentScale = ContentScale.Crop,
        painter = painterResource(id = heroIcon),
        contentDescription = null
    )

}

@Composable
fun HeroInformation(
    @StringRes heroName: Int,
    @StringRes heroDescription: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = heroName),
            style = MaterialTheme.typography.h3
        )
        Text(
            text = stringResource(id = heroDescription),
            style = MaterialTheme.typography.body1
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HeroesPreview() {
    KotlincomposepracticesuperheroesTheme {
        HeroApp()
    }
}