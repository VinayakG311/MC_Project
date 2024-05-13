package com.example.mc_project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mc_project.Databases.UserDao
import com.example.mc_project.Databases.UserDatabase
import com.example.mc_project.Databases.UserEntity
import com.example.mc_project.model.user
import com.example.mc_project.ui.theme.MC_ProjectTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserLogin : ComponentActivity() {
    lateinit var userDatabase: UserDatabase
    lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDatabase = UserDatabase.getDatabase(applicationContext)
        userDao = userDatabase.userdao()
//        GlobalScope.launch {
//            userDao.delete()
//        }
        val action = intent.getStringExtra("action")
        setContent {
            MC_ProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if(action == "login"){
                        LoginScreen(userDao)
                    }
                    else{
                        RegistrationScreen(userDao)
                    }

                }
            }
        }
    }
}



@OptIn(DelicateCoroutinesApi::class)
@Composable
fun RegistrationScreen(userDao: UserDao){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var verifyPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp), // Increased padding for better appearance
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SIGN IN",
            style = MaterialTheme.typography.headlineMedium, // Enhanced typography
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }, // Added icon
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) }, // Added icon
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation(), // Hides the password
        )

        OutlinedTextField(
            value = verifyPassword,
            onValueChange = { verifyPassword = it },
            label = { Text("Verify Your Password") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) }, // Added icon
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation(), // Hides the password
        )

        Button(
            onClick = {

                if (username.isNotEmpty() && password.isNotEmpty() && password == verifyPassword) {
                    val u =userDao.getUser(username)
                    if(u!=null){
                        Toast.makeText(context, "username already exists", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        GlobalScope.launch {
                            userDao.insert(
                                UserEntity(
                                    username = username,
                                    Password = password,
                                    meetings = ""
                                )
                            )
                        }

                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("username", username)
                        context.startActivity(intent)
                    }
                }
                else if(password != verifyPassword){
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
                else {
                    // Handling empty fields
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            shape = CutCornerShape(15)
//            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SIGN IN")
        }

        Button(onClick = {
                         val intent = Intent(context,HomeScreen::class.java)
            context.startActivity(intent)
        },shape = CutCornerShape(10)) {
            Text("Back")
        }


    }

}

@SuppressLint("LogNotTimber")
@Composable
fun LoginScreen(userDao: UserDao) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp), // Increased padding for better appearance
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome",
                style = MaterialTheme.typography.headlineMedium, // Enhanced typography
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }, // Added icon
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) }, // Added icon
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                visualTransformation = PasswordVisualTransformation(), // Hides the password
            )

            Button(
                onClick = {


                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        var u=userDao.getUser(username)
                        var un="";
                        var ps=""
                        try{
                            un=u.username
                            ps=u.Password
                        } catch (e:Exception){
                            Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show()
                        }
                        if(username==un && password==ps){
                            Log.d("success","success")
                            val intent = Intent(context, MainActivity::class.java)
                            intent.putExtra("username", username)
                            context.startActivity(intent)

                        }
                        else{
                            Toast.makeText(context, "Incorrect Password", Toast.LENGTH_SHORT).show()

                        }

                    } else {
                        // Handling empty fields
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = CutCornerShape(15)
//            modifier = Modifier.fillMaxWidth()
            ) {
                Text("LOGIN")
            }



            Button(onClick = {
                val intent = Intent(context,HomeScreen::class.java)
                context.startActivity(intent)
            },shape = CutCornerShape(10)) {
                Text("Back")
            }
        }


}
