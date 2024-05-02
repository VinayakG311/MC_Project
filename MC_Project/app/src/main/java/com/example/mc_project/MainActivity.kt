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



    override fun onCreate(savedInstanceState: Bundle?) {

        var defaultOptions
                = JitsiMeetConferenceOptions.Builder()
            .setServerURL(URL("https://8x8.vc"))
            .setFeatureFlag("welcomepage.enabled", false)
            .build();
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
                            .setToken("eyJraWQiOiJ2cGFhcy1tYWdpYy1jb29raWUtMjJmZmUxODk3YzQyNGE4NWI3Njg0ZDYyY2JlMTFhNTYvZjQyNjkxLVNBTVBMRV9BUFAiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJqaXRzaSIsImlzcyI6ImNoYXQiLCJpYXQiOjE3MTQ1OTEzODIsImV4cCI6MTcxNDU5ODU4MiwibmJmIjoxNzE0NTkxMzc3LCJzdWIiOiJ2cGFhcy1tYWdpYy1jb29raWUtMjJmZmUxODk3YzQyNGE4NWI3Njg0ZDYyY2JlMTFhNTYiLCJjb250ZXh0Ijp7ImZlYXR1cmVzIjp7ImxpdmVzdHJlYW1pbmciOnRydWUsIm91dGJvdW5kLWNhbGwiOnRydWUsInNpcC1vdXRib3VuZC1jYWxsIjpmYWxzZSwidHJhbnNjcmlwdGlvbiI6dHJ1ZSwicmVjb3JkaW5nIjp0cnVlfSwidXNlciI6eyJoaWRkZW4tZnJvbS1yZWNvcmRlciI6ZmFsc2UsIm1vZGVyYXRvciI6dHJ1ZSwibmFtZSI6InZpbmF5YWsyMTExMyIsImlkIjoiZ29vZ2xlLW9hdXRoMnwxMDQ5OTkzMTYwMTI0NjAxNDkwNDAiLCJhdmF0YXIiOiIiLCJlbWFpbCI6InZpbmF5YWsyMTExM0BpaWl0ZC5hYy5pbiJ9fSwicm9vbSI6IioifQ.M-lP0t344IS6ga1QZiXP6MXieJBBHzu6bJe9tZbkTCPfjLQGBI0wbK5mbAIr9Yuc29hcyeKr0bRupIySKQIp-fA3kaUdp_mb1GW0HVLSUtZbVpYq9cGzDh2OA2_s_KlqhnuI9-puH1wVschA9eWl39gUObfMHfeR8SuN-yOBbXP7G1HTQdY73aCpJpcebbtwEQ_o7EQ8_ZiHFrMKb1_qNTh6-iA-HaeYMqll_BlE5sJKPA4A8hISSFEPROMOOsERLOLFo9zMm8-zhQRb4ewpIKF6-xjqsq4sZjLX3F8JuFSULpkB9NR6yYsigsh8UTTEDd4RmWkZFRbLCTL9AR7f7w")
                            .setRoom("vpaas-magic-cookie-22ffe1897c424a85b7684d62cbe11a56/DirectFactoriesDanceSubsequently") // Settings for audio and video
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