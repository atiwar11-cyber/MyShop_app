package com.example.myshopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myshopapp.ui.theme.MyshopappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyshopappTheme {
                ShopApp()
            }
        }
    }
}

@Composable
fun ShopApp(viewModel: ShopViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf("onboarding") }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    when (currentScreen) {
        "onboarding" -> OnboardingScreen { currentScreen = "home" }
        "home" -> HomeScreen(
            onProductClick = {
                selectedProduct = it
                currentScreen = "details"
            },
            onCartClick = { currentScreen = "cart" },
            viewModel = viewModel
        )
        "details" -> ProductDetailScreen(
            product = selectedProduct!!,
            onBack = { currentScreen = "home" },
            onAddToCart = {
                viewModel.addToCart(it)
                currentScreen = "cart"
            }
        )
        "cart" -> CartScreen(
            viewModel = viewModel,
            onBack = { currentScreen = "home" },
            onCheckout = {
                viewModel.clearCart()
                currentScreen = "home"
            }
        )
    }
}

@Composable
fun OnboardingScreen(onGetStarted: () -> Unit) {
    val context = LocalContext.current
    val heroImageRes = context.resources.getIdentifier("phone", "drawable", context.packageName)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF1A237E))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Brand section
            Surface(
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(50),
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF00E5FF), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "TECH HAVEN PREMIUM",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "The Future\nin Your Hands",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 52.sp
                ),
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                // Background Glow
                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color(0xFF00E5FF).copy(alpha = 0.15f), Color.Transparent)
                            )
                        )
                )
                
                if (heroImageRes != 0) {
                    Image(
                        painter = painterResource(id = heroImageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(0.85f)
                            .padding(bottom = 40.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            // Bottom Panel
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Explore the next generation of smart devices and high-performance electronics.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    Button(
                        onClick = onGetStarted,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF1A237E)
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Get Started", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onProductClick: (Product) -> Unit, onCartClick: () -> Unit, viewModel: ShopViewModel) {
    val totalPrice by viewModel.totalPrice.collectAsState()
    var selectedCategory by remember { mutableStateOf("All") }

    Scaffold(
        containerColor = Color(0xFFF8F9FF),
        bottomBar = {
            Surface(
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .navigationBarsPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Home, contentDescription = "Home", tint = Color(0xFF1A237E))
                    }
                    IconButton(onClick = onCartClick) {
                        BadgedBox(
                            badge = {
                                if (viewModel.cartItems.isNotEmpty()) {
                                    Badge(containerColor = Color(0xFFD32F2F)) {
                                        Text(viewModel.cartItems.size.toString(), color = Color.White)
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.Gray)
                        }
                    }
                    Text(
                        text = "Total: $${String.format("%.2f", totalPrice)}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.Gray)
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.Gray)
                    }
                }
            }
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 120.dp
            ),
            userScrollEnabled = true,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) { Icon(Icons.Default.Menu, contentDescription = "Menu") }
                    Text("Tech Haven", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF1A237E))
                    IconButton(onClick = {}) { Icon(Icons.Default.Search, contentDescription = "Search") }
                }
            }

            // Banner
            item(span = { GridItemSpan(maxLineSpan) }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF6))
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("NEW RELEASE", color = Color(0xFF3F51B5), fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            Text(
                                "Cutting-Edge\nGadgets",
                                color = Color.Black,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                lineHeight = 28.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {},
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                                modifier = Modifier.height(36.dp)
                            ) {
                                Text("Explore Now", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }

            // Categories
            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(listOf("All", "Smartphones", "Laptops", "Audio", "Wearables")) { category ->
                        val isSelected = category == selectedCategory
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) Color(0xFF1A237E) else Color.White,
                            modifier = Modifier.clickable { selectedCategory = category },
                            shadowElevation = 2.dp
                        ) {
                            Text(
                                text = category,
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                                color = if (isSelected) Color.White else Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Product List Title
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    "Featured Products",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Grid Items
            val filteredProducts = if (selectedCategory == "All") sampleProducts else sampleProducts.filter { it.category == selectedCategory }
            items(filteredProducts) { product ->
                ProductItem(product = product, onClick = { onProductClick(product) })
            }

            // Footer Spacer
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    val context = LocalContext.current
    val imageRes = context.resources.getIdentifier(product.imageResName, "drawable", context.packageName)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                if (imageRes != 0) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                product.category,
                color = Color(0xFF3F51B5),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                maxLines = 1,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "$${String.format("%.2f", product.price)}",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Surface(
                    onClick = onClick,
                    modifier = Modifier.size(32.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF1A237E)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetailScreen(product: Product, onBack: () -> Unit, onAddToCart: (Product) -> Unit) {
    val context = LocalContext.current
    val imageRes = context.resources.getIdentifier(product.imageResName, "drawable", context.packageName)
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
                        .size(40.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Specifications", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
                        .size(40.dp)
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                if (imageRes != 0) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(40.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        product.name,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black,
                        lineHeight = 32.sp
                    )
                    Text(
                        product.category,
                        color = Color(0xFF3F51B5),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    "$${String.format("%.2f", product.price)}",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF1A237E)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    " 4.8 (1.2k Reviews)",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("About this Item", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                product.description,
                color = Color.Gray,
                fontSize = 15.sp,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Storage Options", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("128GB", "256GB", "512GB").forEach { storage ->
                    val isSelected = storage == "256GB"
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) Color(0xFF1A237E) else Color.White,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) Color(0xFF1A237E) else Color(0xFFEEEEEE)
                        ),
                        modifier = Modifier.clickable { }
                    ) {
                        Text(
                            text = storage,
                            modifier = Modifier.padding(horizontal = 22.dp, vertical = 14.dp),
                            color = if (isSelected) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { onAddToCart(product) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text("Add to Cart", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(viewModel: ShopViewModel, onBack: () -> Unit, onCheckout: () -> Unit) {
    val cartItems = viewModel.cartItems
    val totalPrice by viewModel.totalPrice.collectAsState()
    val context = LocalContext.current

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Cart", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            if (cartItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                            tint = Color(0xFFF0F0F0)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Your cart is empty", color = Color.Gray, fontSize = 18.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(cartItems) { product ->
                        val imageRes = context.resources.getIdentifier(product.imageResName, "drawable", context.packageName)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF9F9F9), RoundedCornerShape(24.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .background(Color.White, RoundedCornerShape(16.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (imageRes != 0) {
                                    Image(
                                        painter = painterResource(id = imageRes),
                                        contentDescription = product.name,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(12.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(product.name, fontWeight = FontWeight.Bold, maxLines = 1, fontSize = 16.sp)
                                Text(product.category, color = Color(0xFF3F51B5), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    "$${String.format("%.2f", product.price)}",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp
                                )
                            }
                            IconButton(
                                onClick = { viewModel.removeFromCart(product) },
                                modifier = Modifier.background(Color.White, RoundedCornerShape(12.dp))
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color(0xFFD32F2F))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider(color = Color(0xFFF0F0F0))
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Price", fontSize = 18.sp, color = Color.Gray)
                    Text(
                        "$${String.format("%.2f", totalPrice)}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1A237E)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onCheckout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E)),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text("Checkout", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
