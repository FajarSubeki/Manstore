package id.manstore.module.product.presentation.product_details

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import id.manstore.module.product.domain.model.Product
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.manstore.R
import id.manstore.core.presentation.theme.GrayColor
import id.manstore.core.presentation.theme.MainWhiteColor
import id.manstore.core.presentation.theme.PrimaryColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun ProductDetailsScreen(
    product: Product,
    navigator: DestinationsNavigator
) {

    Scaffold(
        backgroundColor = Color.White,
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navigator.popBackStack()
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_left),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    ) {
        DetailsScreenContent(
            product = product,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun DetailsScreenContent(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Column {
        Box(modifier = modifier.weight(1f), contentAlignment = Alignment.Center) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = product.image)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            placeholder(R.drawable.ic_placeholder)
                        }).build()
                ),
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Inside
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = modifier
                .fillMaxWidth()
                .weight(2f),
            elevation = 0.dp,
            shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp),
            backgroundColor = MainWhiteColor
        ) {

            Box(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = product.title,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val rating: Float by remember { mutableStateOf(product.rating.rate.toFloat()) }

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RatingBar(
                            value = rating,
                            config = RatingBarConfig()
                                .activeColor(PrimaryColor)
                                .inactiveColor(GrayColor)
                                .stepSize(StepSize.HALF)
                                .numStars(5)
                                .isIndicator(true)
                                .size(16.dp)
                                .padding(3.dp)
                                .style(RatingBarStyle.HighLighted),
                            onValueChange = {},
                            onRatingChanged = {}
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "(${product.rating.count})",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = product.description,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )

                }
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$${product.price}",
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = PrimaryColor,
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.add_to_cart)
                        )
                    }
                }
            }
        }
    }
}