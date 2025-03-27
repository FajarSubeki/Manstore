@file:OptIn(ExperimentalMaterialApi::class)

package id.manstore.module.product.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import id.manstore.module.product.domain.model.Product
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.manstore.R
import id.manstore.core.presentation.theme.DarkBlue
import id.manstore.core.presentation.theme.MainWhiteColor
import id.manstore.core.presentation.theme.PrimaryColor
import id.manstore.core.util.LoadingAnimation
import id.manstore.core.util.UiEvents
import id.manstore.module.auth.domain.model.User
import id.manstore.module.destinations.LoginScreenDestination
import id.manstore.module.destinations.ProductDetailsScreenDestination
import id.manstore.module.profile.account.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val productsState = viewModel.productsState.value
    val categories = viewModel.categoriesState.value
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true, block = {
        profileViewModel.getProfile()
    })

    val user = profileViewModel.profileState.value

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            ProfileContent(
                user = user,
                onLogout = {
                    coroutineScope.launch {
                        sheetState.hide()
                        profileViewModel.logout()
                        navigator.popBackStack()
                        navigator.navigate(LoginScreenDestination.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                onClose = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                }
            )
        },
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Scaffold(
            topBar = {
                MyTopAppBarWithBottomSheet(user, sheetState, coroutineScope)
            },
        ) {
            val scaffoldState = rememberScaffoldState()

            LaunchedEffect(key1 = true) {
                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is UiEvents.SnackbarEvent -> {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = event.message
                            )
                        }
                        else -> {}
                    }
                }
            }

            HomeScreenContent(
                categories = categories,
                productsState = productsState,
                navigator = navigator,
                selectedCategory = viewModel.selectedCategory.value,
                onSelectCategory = { category ->
                    viewModel.setCategory(category)
                    viewModel.getProducts(viewModel.selectedCategory.value)
                }
            )
        }
    }
}

@Composable
private fun HomeScreenContent(
    categories: List<String>,
    productsState: ProductsState,
    navigator: DestinationsNavigator,
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp)
        ) {
            item(span = { GridItemSpan(2) }) { Spacer(modifier = Modifier.height(16.dp)) }

            item(span = { GridItemSpan(2) }) {
                Categories(
                    categories = categories,
                    onSelectCategory = onSelectCategory,
                    selectedCategory = selectedCategory
                )
            }

            item(span = { GridItemSpan(2) }) { Spacer(modifier = Modifier.height(12.dp)) }

            items(productsState.products) { product ->
                ProductItem(
                    product = product,
                    navigator = navigator,
                    modifier = Modifier.width(150.dp)
                )
            }
        }

        if (productsState.isLoading) {
            LoadingAnimation(
                modifier = Modifier.align(Center),
                circleSize = 16.dp,
            )
        }

        productsState.error?.let {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Center).padding(16.dp),
                text = it,
                color = Color.Red
            )
        }
    }
}

@Composable
private fun ProductItem(
    product: Product,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(6.dp)
            .clickable {
                navigator.navigate(ProductDetailsScreenDestination(product))
            },
        elevation = 4.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = product.image)
                        .apply {
                            placeholder(R.drawable.ic_placeholder)
                            crossfade(true)
                        }.build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .align(CenterHorizontally),
                contentScale = ContentScale.Inside
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.category,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraLight
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$${product.price}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MyTopAppBarWithBottomSheet(
    user: User,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope,
) {
    TopAppBar(
        title = { Text("Hi, " + (user.name?.firstname ?: "")) },
        actions = {
            IconButton(onClick = {
                coroutineScope.launch {
                    sheetState.show()
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Profile",
                    tint = DarkBlue
                )
            }
        },
        backgroundColor = Color.White
    )
}

@Composable
fun ProfileContent(user: User, onLogout: () -> Unit, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Tombol X di sudut kanan atas
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd) // Dapat digunakan di dalam Box
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.Gray
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Center), // Pusatkan konten di dalam Box
            horizontalAlignment = CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.profile_img),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${user.name?.firstname.orEmpty()} ${user.name?.lastname.orEmpty()}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            user.email?.let {
                Text(text = it, fontSize = 14.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onLogout) {
                Text("Logout")
            }
        }
    }
}

@Composable
fun Categories(
    categories: List<String>,
    onSelectCategory: (String) -> Unit,
    selectedCategory: String,
) {
    LazyRow(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (category == selectedCategory) {
                            PrimaryColor
                        } else {
                            MainWhiteColor
                        }
                    )
                    .clickable { onSelectCategory(category) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category,
                    style = typography.body1,
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }
    }
}

