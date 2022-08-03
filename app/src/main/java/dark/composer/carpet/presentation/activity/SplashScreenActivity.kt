package dark.composer.carpet.presentation.activity

import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import dark.composer.carpet.R

class SplashScreenActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}