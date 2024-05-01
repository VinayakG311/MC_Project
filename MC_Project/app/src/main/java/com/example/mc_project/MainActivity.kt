package com.example.mc_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mc_project.ui.theme.MC_ProjectTheme
import com.facebook.react.modules.core.PermissionListener
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetActivityInterface
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.URL

class MainActivity : ComponentActivity(),JitsiMeetActivityInterface {

    var defaultOptions
            = JitsiMeetConferenceOptions.Builder()
        .setServerURL(URL("https://meet.jit.si/"))
        .setFeatureFlag("welcomepage.enabled", false)
        .build();

    override fun onCreate(savedInstanceState: Bundle?) {
        JitsiMeet.setDefaultConferenceOptions(defaultOptions)
        super.onCreate(savedInstanceState)
        setContent {

                // A surface container using the 'background' color from the theme
                Surface(

                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    Button(onClick = {
                        val options = JitsiMeetConferenceOptions.Builder()
                            .setRoom("room1") // Settings for audio and video
                            .build()

                        JitsiMeetActivity.launch(this, options);
                    }){
                        Text(text = "Create Room")
                    }
                }

        }
    }

    override fun requestPermissions(p0: Array<out String>?, p1: Int, p2: PermissionListener?) {
        TODO("Not yet implemented")
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
    MC_ProjectTheme {
        Greeting("Android")
    }
}