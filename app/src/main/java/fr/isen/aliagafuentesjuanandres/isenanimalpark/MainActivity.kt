package fr.isen.aliagafuentesjuanandres.isenanimalpark

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import fr.isen.aliagafuentesjuanandres.isenanimalpark.ui.theme.IsenAnimalParkTheme

class MainActivity : ComponentActivity() {
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        databaseReference = FirebaseDatabase.getInstance().reference

        //writeDataToFirebase()

        databaseReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               Log.d("firebase", "snapshot")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        setContent {
            IsenAnimalParkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun writeDataToFirebase() {
        databaseReference.setValue("here there")
            .addOnSuccessListener(OnSuccessListener<Void> {
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                Log.d("Firebase", "Data successfully written!")
            })
            .addOnFailureListener(OnFailureListener { e ->
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                Log.e("Firebase", "Failed to write data", e)
            })
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "Write operation completed successfully")
                } else {
                    Log.e("Firebase", "Write operation failed", task.exception)
                }
            })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IsenAnimalParkTheme {
        Greeting("Android")
    }
}
