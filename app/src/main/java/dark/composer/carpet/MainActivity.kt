package dark.composer.carpet

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.android.support.DaggerAppCompatActivity
import dark.composer.carpet.databinding.ActivityMainBinding
import dark.composer.carpet.utils.Constants

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