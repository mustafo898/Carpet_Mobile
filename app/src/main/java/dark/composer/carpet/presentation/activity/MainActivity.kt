package dark.composer.carpet.presentation.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import dark.composer.carpet.R
import dark.composer.carpet.databinding.ActivityMainBinding
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var controller: NavController

    @Inject
    lateinit var shared: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        controller = findNavController(R.id.main_nav_fragment)

        binding.bottomNavigation.setupWithNavController(controller)
    }
}