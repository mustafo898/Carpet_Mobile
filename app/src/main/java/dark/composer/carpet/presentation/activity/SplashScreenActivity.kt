package dark.composer.carpet.presentation.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import dagger.android.support.DaggerAppCompatActivity
import dark.composer.carpet.R

@SuppressLint("CustomSplashScreen")
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
    }
}