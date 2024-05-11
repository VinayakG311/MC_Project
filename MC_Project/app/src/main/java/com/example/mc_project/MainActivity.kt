package com.example.mc_project

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mc_project.Databases.MeetDao
import com.example.mc_project.Databases.MeetDatabase
import com.example.mc_project.Databases.MeetEntity
import com.example.mc_project.Databases.UserDao
import com.example.mc_project.Databases.UserDatabase
import com.example.mc_project.Databases.UserEntity
import com.example.mc_project.ui.theme.MC_ProjectTheme
import com.facebook.react.modules.core.PermissionListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetActivityInterface
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.URL

class MainActivity : ComponentActivity(), JitsiMeetActivityInterface {
    lateinit var userDatabase: UserDatabase
    lateinit var userDao: UserDao
    lateinit var meetDatabase: MeetDatabase
    lateinit var meetDao: MeetDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDatabase = UserDatabase.getDatabase(applicationContext)
        userDao = userDatabase.userdao()

        meetDatabase = MeetDatabase.getDatabase(applicationContext)
        meetDao = meetDatabase.meetdao()

        val defaultOptions = JitsiMeetConferenceOptions.Builder()
            .setServerURL(URL("https://meet.jit.si"))
            .setFeatureFlag("welcomepage.enabled", false)
            .build()

        JitsiMeet.setDefaultConferenceOptions(defaultOptions)

        val username = intent.getStringExtra("username") ?: "User"

        setContent {
            MC_ProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(username,userDao)
                }
            }
        }
    }


    @Composable
    fun MainScreen(username: String,userDao: UserDao) {
        var isLoading by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf("") }
        var create by remember { mutableStateOf(false) }
        var join by remember { mutableStateOf(false) }
        var Roomname by remember { mutableStateOf("") }
        var existing by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            }
            if(!create && !join){
                Text(
                    text = "Welcome, $username!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = {
                        create=true
                        join=false
                        existing = false

                    },

                    shape = CutCornerShape(15),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp, horizontal = 50.dp)
                ) {
                    Text("Create New Room")
                }

                Button(
                    onClick = {
                        create=false
                        join=true
                        existing = false

                    },
                    shape = CutCornerShape(15),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp, horizontal = 50.dp)
                ) {
                    Text("Join Existing Room")
                }

                Button(
                    onClick = {
                        create=false
                        join=false
                        existing = true

                    },
                    shape = CutCornerShape(15),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp, horizontal = 50.dp)
                ) {
                    Text("Existing Meeting Details")
                }
            }

            if(create){
                TextField(value = Roomname, keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),onValueChange = {Roomname=it}, label = { Text(text = "Room name")})
                Button(
                    onClick = {
                        isLoading = true
                        try {
                            val options = JitsiMeetConferenceOptions.Builder()
                                .setRoom(Roomname)
                                .build()

                            val userDetails = userDao.getUser(username)
                            var meetingDetails = userDetails.meetings
                            meetingDetails = meetingDetails+" "+Roomname
                            val password = userDetails.Password
                            GlobalScope.launch {
                                meetDao.insert(MeetEntity(Roomname,username))
                            }
                            GlobalScope.launch {
                                userDao.insert(UserEntity(username,password,meetingDetails))
                            }


                            JitsiMeetActivity.launch(this@MainActivity, options)

                            isLoading = false
                        } catch (e: Exception) {
                            isLoading = false
                            errorMessage = "Failed to start the meeting: ${e.localizedMessage}"
                        }
                    },
                    shape = CutCornerShape(10)
//                    modifier =  Modifier.width(100 .dp).height(50 .dp)
                ) {
                    Text("Create")
                }

            }
            if (join){
                TextField(value = Roomname, onValueChange = {Roomname=it},keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done), label = { Text(text = "Room name")})
                Button(
                    onClick = {
                        isLoading = true
                        try {
                            val options = JitsiMeetConferenceOptions.Builder()
                                .setRoom(Roomname)
                                .build()
                            val userDetails = userDao.getUser(username)
                            var meetingDetails = userDetails.meetings
                            meetingDetails = meetingDetails+" "+Roomname
                            val password = userDetails.Password
                            var meetParticipants = meetDao.getMeet(Roomname).Participants
                            meetParticipants = meetingDetails+" "+username
                            GlobalScope.launch {
                                meetDao.insert(MeetEntity(Roomname,meetParticipants))
                            }
                            GlobalScope.launch {
                                userDao.insert(UserEntity(username,password,meetingDetails))
                            }
                            JitsiMeetActivity.launch(this@MainActivity, options)

                            isLoading = false
                        } catch (e: Exception) {
                            isLoading = false
                            errorMessage = "Failed to start the meeting: ${e.localizedMessage}"
                        }
                    },
                    shape = CutCornerShape(10)
                ) {
                    Text("Join")
                }
            }

            if(existing) {
                var meetingsString = userDao.getUser(username).meetings
                Log.d("VINAYAK12"," ${username} ${meetingsString}")
                val meetings = meetingsString.split(" ")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(meetings) { meeting ->
                        if (meeting.isNotEmpty()) {
                        val currentMeet = meetDao.getMeet(meeting)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),

                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .background(Color.White)
                            ) {
                                Text(text = "Meeting Name - ${currentMeet.Id}")
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Participants - ${currentMeet.Participants.split(" ").size}")
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        }
                    }


                }
            }
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }
    }

    override fun requestPermissions(p0: Array<out String>?, p1: Int, p2: PermissionListener?) {
        TODO("Not yet implemented")
    }
}


//@Composable
//fun VideoCallScreen(){
//    Button(onClick = {
//        val options = JitsiMeetConferenceOptions.Builder()
//            .setRoom("room1")
//            .build()
//        //for mods:
////                            .setToken("eyJhbGciOiJSUzI1NiIsImtpZCI6Ii0tLS0tQkVHSU4gUFVCTElDIEtFWS0tLS0tXG5NSUlCSWpBTkJna3Foa2lHOXcwQkFRRUZBQU9DQVE4QU1JSUJDZ0tDQVFFQWxjN2pnN1B1T0ZJSXQ4a2pDakVDXG5zd3FZc3F0TlIyQzhBamVlbDR5eFdwRnR1N1BNZ0F0cm5aVk5ydWhLTk1qNkkzMlRZUllWOG9UWWhTakI4ZjE5XG53czRnd1UzcmhiK3RkancrQWVYbFBrVHNiM0k5Z2Nhbk81MWZuYkJBVkhWWXR1bGtVVkRYOXdvc21ielo3NXptXG5obE5VQ25XRDdVK2N2aXEwc29ka2RyUXlMZWxEd3M2Q05BZ3EzR2pJTkxFSDZXeTVvVXN6MUl4R0o1dkk5cnUzXG5BQ291Qm9NVWorcUtvZEFCUDZFdThITnFYWWgrZzFIdG5DcWtZdmpQRGcwY2xxeVRMbkpMUGtXdEF0eW56bStTXG5IdzhMbEtMcXhRUHZMRWFiSXY0akJjUGdQMHUzVXhNem1xVGFpM0l0NDJ1Y2lYNUdZT1ZLM1o5Q2RwNGMyblpnXG5Fd0lEQVFBQlxuLS0tLS1FTkQgUFVCTElDIEtFWS0tLS0tXG4iLCJ0eXAiOiJKV1QifQ.eyJleHAiOjE3MTQ2MzgwNjMsIm5iZlRpbWUiOjE3MTQ2MzA4NTMsInJvb20iOiIqIiwic3ViIjoidnBhYXMtbWFnaWMtY29va2llLTIyZmZlMTg5N2M0MjRhODViNzY4NGQ2MmNiZTExYTU2IiwiY29udGV4dCI6eyJ1c2VyIjp7Im1vZGVyYXRvciI6InRydWUiLCJpZCI6ImMyYjkzMTU1LWYwMzItNGVjYy04NzUyLTMxNjU5MzNiNzRhNCIsIm5hbWUiOiJuYW1lMSIsImVtYWlsIjoiZW1haWwxIn0sImZlYXR1cmVzIjp7ImxpdmVzdHJlYW1pbmciOiJ0cnVlIiwicmVjb3JkaW5nIjoidHJ1ZSIsIm91dGJvdW5kLWNhbGwiOiJ0cnVlIiwidHJhbnNjcmlwdGlvbiI6InRydWUifX0sImlzcyI6ImNoYXQiLCJhdWQiOiJqaXRzaSJ9.Z_yLDhibgaVsfx0bv8bE7WHZYuZWER2vWd7ZjRuEwKZtd7Y8z6yKHGtiaf7jV42ruWGBFd3CtqPGPsqolgxihuzMwSGRUYJjLrFR0gp9qXFiXG-7y4d2260tjDUxijcYySFbQBiijKGhqftFmU3mCp63Xw-9jO0uF1FrpUDN4jq62detSyGdpXJiI7Kvx1-wVGmxryjKyTrNoXdA-q2kKp9sr8uJT0vZ4pxRrzwIsfgiTTRdkod3_TUin4L7A6eD-ny77PS-6x9_2fBt6KMbFp1ZFdhX0tzI927LF_uKlzWAZHpOp7JCB6lLSk_bYUAMllhynTBCYDIiLpMonfpiQg")
////                            .setRoom("vpaas-magic-cookie-22ffe1897c424a85b7684d62cbe11a56/myroom") // Settings for audio and video
////                            .build()
//
//        JitsiMeetActivity.launch(this, options);
//    }){
//        Text(text = "Create Room")
//    }
//}
//}



