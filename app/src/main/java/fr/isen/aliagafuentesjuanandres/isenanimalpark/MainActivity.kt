package fr.isen.aliagafuentesjuanandres.isenanimalpark

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.*
import fr.isen.aliagafuentesjuanandres.isenanimalpark.ui.theme.IsenAnimalParkTheme

// Data class to represent an animal
data class Animal(
    val id: String = "",
    val name: String = "",
    val species: String = "",
    val age: Int = 0,
    val description: String = ""
)

class MainActivity : ComponentActivity() {
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        databaseReference = FirebaseDatabase.getInstance().reference

        // Add sample animals to database
        addSampleAnimals()

        setContent {
            IsenAnimalParkTheme {
                AnimalParkApp(databaseReference)
            }
        }
    }

    private fun addSampleAnimals() {
        val animalsRef = databaseReference.child("animals")

        val animals = listOf(
            Animal("1", "Leo", "Lion", 5, "Majestic male lion with a full mane"),
            Animal("2", "Ellie", "Elephant", 12, "Female elephant known for her gentle nature"),
            Animal("3", "Charlie", "Chimpanzee", 8, "Playful and intelligent chimp"),
            Animal("4", "Melman", "Giraffe", 7, "Tall giraffe with distinctive spots")
        )

        animals.forEach { animal ->
            animalsRef.child(animal.id).setValue(animal)
                .addOnFailureListener { e ->
                    Log.e("Firebase", "Error adding animal: ${animal.name}", e)
                }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalParkApp(databaseReference: DatabaseReference) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ISEN Animal Park") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen(databaseReference)
            }
            composable("events") {
                EventsScreen()
            }
            composable("history") {
                HistoryScreen()
            }
            composable("shopping") {
                ShoppingScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(databaseReference: DatabaseReference) {
    var animals by remember { mutableStateOf<List<Animal>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Effect to load animals from Firebase
    LaunchedEffect(key1 = Unit) {
        val animalsRef = databaseReference.child("animals")
        animalsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val animalsList = mutableListOf<Animal>()
                for (animalSnapshot in snapshot.children) {
                    val animal = animalSnapshot.getValue(Animal::class.java)
                    animal?.let { animalsList.add(it) }
                }
                animals = animalsList
                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                errorMessage = "Failed to load animals: ${error.message}"
                isLoading = false
            }
        })
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
            animals.isEmpty() -> {
                Text(
                    text = "No animals found",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(animals) { animal ->
                        AnimalCard(animal = animal)
                    }
                }
            }
        }
    }
}

@Composable
fun EventsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Events Screen - Coming Soon!")
    }
}

@Composable
fun HistoryScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "History Screen - Coming Soon!")
    }
}

@Composable
fun ShoppingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "I don't know Screen - Coming Soon!")
    }
}

@Composable
fun AnimalCard(animal: Animal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = animal.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Species: ${animal.species}",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Age: ${animal.age} years",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = animal.description,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // Esta lista guarda los nombres de cada pantalla para la navegación
    val items = listOf("home", "events", "history", "shopping")

    // Esta lista sirve para guardar los iconos de cada pantalla
    val icons = listOf(
        Icons.Filled.Home,   // Icono de la pantalla principal
        Icons.Filled.Event,  // Icono de la pantalla de eventos
        Icons.Filled.History, // Icono de la pantalla de historial
        Icons.Filled.CreditCard    // Icono de la pantalla de compra
    )

    // Esta lista almacena las etiquetas que se mostrarán en la barra de navegación
    val labels = listOf("Home", "Events", "History", "Shopping")

    // Variable para almacenar el índice del ítem seleccionado en la barra de navegación
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    // Se crea la barra de navegación inferior con fondo blanco
    NavigationBar(
        containerColor = Color.White
    ) {
        // Se genera cada ítem de la barra de navegación
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = labels[index]
                    )
                },
                label = {
                    Text(text = labels[index])
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index // Se actualiza el ítem seleccionado
                    navController.navigate(screen) // Se navega a la pantalla seleccionada
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6200EE), // Color del icono cuando está seleccionado
                    unselectedIconColor = Color(0xFF6200EE).copy(alpha = 0.6f), // Color del icono cuando no está seleccionado
                    selectedTextColor = Color(0xFF6200EE), // Color del texto cuando está seleccionado
                    unselectedTextColor = Color(0xFF6200EE).copy(alpha = 0.6f), // Color del texto cuando no está seleccionado
                    indicatorColor = Color.Transparent // Se mantiene sin indicador visual de selección
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimalCardPreview() {
    IsenAnimalParkTheme {
        AnimalCard(
            Animal(
                "1",
                "Leo",
                "Lion",
                5,
                "Majestic male lion with a full mane"
            )
        )
    }
}