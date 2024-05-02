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
            .setServerURL(URL("https://meet.jit.si"))
            .setFeatureFlag("welcomepage.enabled", false)
            .build();
        JitsiMeet.setDefaultConferenceOptions(defaultOptions)
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("username")
        setContent {

                // A surface container using the 'background' color from the theme
                Surface(

                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")

                    Button(onClick = {
                        val options = JitsiMeetConferenceOptions.Builder()
                            .setRoom("room1")
                            .build()
                            //for mods:
//                            .setToken("eyJhbGciOiJSUzI1NiIsImtpZCI6Ii0tLS0tQkVHSU4gUFVCTElDIEtFWS0tLS0tXG5NSUlCSWpBTkJna3Foa2lHOXcwQkFRRUZBQU9DQVE4QU1JSUJDZ0tDQVFFQWxjN2pnN1B1T0ZJSXQ4a2pDakVDXG5zd3FZc3F0TlIyQzhBamVlbDR5eFdwRnR1N1BNZ0F0cm5aVk5ydWhLTk1qNkkzMlRZUllWOG9UWWhTakI4ZjE5XG53czRnd1UzcmhiK3RkancrQWVYbFBrVHNiM0k5Z2Nhbk81MWZuYkJBVkhWWXR1bGtVVkRYOXdvc21ielo3NXptXG5obE5VQ25XRDdVK2N2aXEwc29ka2RyUXlMZWxEd3M2Q05BZ3EzR2pJTkxFSDZXeTVvVXN6MUl4R0o1dkk5cnUzXG5BQ291Qm9NVWorcUtvZEFCUDZFdThITnFYWWgrZzFIdG5DcWtZdmpQRGcwY2xxeVRMbkpMUGtXdEF0eW56bStTXG5IdzhMbEtMcXhRUHZMRWFiSXY0akJjUGdQMHUzVXhNem1xVGFpM0l0NDJ1Y2lYNUdZT1ZLM1o5Q2RwNGMyblpnXG5Fd0lEQVFBQlxuLS0tLS1FTkQgUFVCTElDIEtFWS0tLS0tXG4iLCJ0eXAiOiJKV1QifQ.eyJleHAiOjE3MTQ2MzgwNjMsIm5iZlRpbWUiOjE3MTQ2MzA4NTMsInJvb20iOiIqIiwic3ViIjoidnBhYXMtbWFnaWMtY29va2llLTIyZmZlMTg5N2M0MjRhODViNzY4NGQ2MmNiZTExYTU2IiwiY29udGV4dCI6eyJ1c2VyIjp7Im1vZGVyYXRvciI6InRydWUiLCJpZCI6ImMyYjkzMTU1LWYwMzItNGVjYy04NzUyLTMxNjU5MzNiNzRhNCIsIm5hbWUiOiJuYW1lMSIsImVtYWlsIjoiZW1haWwxIn0sImZlYXR1cmVzIjp7ImxpdmVzdHJlYW1pbmciOiJ0cnVlIiwicmVjb3JkaW5nIjoidHJ1ZSIsIm91dGJvdW5kLWNhbGwiOiJ0cnVlIiwidHJhbnNjcmlwdGlvbiI6InRydWUifX0sImlzcyI6ImNoYXQiLCJhdWQiOiJqaXRzaSJ9.Z_yLDhibgaVsfx0bv8bE7WHZYuZWER2vWd7ZjRuEwKZtd7Y8z6yKHGtiaf7jV42ruWGBFd3CtqPGPsqolgxihuzMwSGRUYJjLrFR0gp9qXFiXG-7y4d2260tjDUxijcYySFbQBiijKGhqftFmU3mCp63Xw-9jO0uF1FrpUDN4jq62detSyGdpXJiI7Kvx1-wVGmxryjKyTrNoXdA-q2kKp9sr8uJT0vZ4pxRrzwIsfgiTTRdkod3_TUin4L7A6eD-ny77PS-6x9_2fBt6KMbFp1ZFdhX0tzI927LF_uKlzWAZHpOp7JCB6lLSk_bYUAMllhynTBCYDIiLpMonfpiQg")
//                            .setRoom("vpaas-magic-cookie-22ffe1897c424a85b7684d62cbe11a56/myroom") // Settings for audio and video
//                            .build()

                        JitsiMeetActivity.launch(this, options);
                    }){
                        Text(text = " ${username} Create Room")
                    }
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