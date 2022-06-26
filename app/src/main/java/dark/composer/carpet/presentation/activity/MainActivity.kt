package dark.composer.carpet.presentation.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import dagger.android.support.DaggerAppCompatActivity
import dark.composer.carpet.R
import dark.composer.carpet.databinding.ActivityMainBinding

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var controller: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        controller = findNavController(R.id.main_nav_fragment)
//        appBarConfiguration = AppBarConfiguration(controller.graph)
//        setupActionBarWithNavController(controller, appBarConfiguration)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return controller.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
}