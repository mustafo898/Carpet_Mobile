package dark.composer.carpet.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import dagger.android.support.DaggerAppCompatActivity
import dark.composer.carpet.R

class SplashScreenActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val intent = Intent(this, MainActivity::class.java)

        try {
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.d("EEEE", "SplashScreen: $e")
        }


//        // Hostname is defined so do have arbitrary
//        // localhost value which is nothing but 127.0.0.1
//        val hostname = "195.2.85.176"
//
//        // PORT is defined here
//        // It should have been 8080 or 8000 but cannot be 80
//
//        // If IP and PORT is invalid it will get exception
//        // Trying to connect
//        val port = 9001
//
//        // Try block to check for exceptions
//
//        val intent = Intent(this,MainActivity::class.java)
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                Socket(Constants.BASE_URL, port).use { socket ->
//
//                    // InputStream to read data from socket
//                    val inputStream: InputStream = socket.getInputStream()
//                    val inputStreamReader = InputStreamReader(inputStream)
//                    var data: Int
//                    val outputString = StringBuilder()
//
//                    // Data read from input stream
//                    while (inputStreamReader.read().also { data = it }
//                        != -1
//                    ) {
//                        outputString.append(data.toChar())
//                        startActivity(intent)
//                        finish()
//                    }
//                }
//            } // Catch block to handle the exceptions
//            catch (ex: IOException) {
//
//                // If the given hostname and port number are
//                // invalid, connectivity cannot be established
//                // and hence error thrown Exception will happen
//                // when socket will not reachable
//                System.out.println(
//                    "Connection Refused Exception as the given hostname and port are invalid : "
//                            + ex.message
//                )
//            }
//        }
//

    }
}