package dark.composer.carpet.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import dark.composer.carpet.R
import dark.composer.carpet.databinding.ActivityMainBinding
import dark.composer.carpet.presentation.fragment.admin.AdminFragment
import dark.composer.carpet.presentation.fragment.deafaults.DefaultFragment
import dark.composer.carpet.utils.SharedPref
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    //    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var controller: NavController

    @Inject
    lateinit var shared: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        controller = findNavController(R.id.main_nav_fragment)
//        appBarConfiguration = AppBarConfiguration(controller.graph)
//        setupActionBarWithNavController(controller, appBarConfiguration)

        binding.bottomNavigation.setupWithNavController(controller)
//        checkPermission()

        val adminFragment = AdminFragment()
        val defaultFragment = DefaultFragment()
        val fragment = Fragment()

//        if (shared.getRole() == "ADMIN") {
//            startActivityFromFragment(AdminFragment(),Intent(this, MainActivity::class.java))
            supportFragmentManager.beginTransaction().add(adminFragment,"ADMIN")
            findViewById<BottomNavigationView>(R.id.bottomNavigation)?.visibility = View.VISIBLE
//        } else {
////            startActivityFromFragment(DefaultFragment(),Intent(this, MainActivity::class.java))
//            supportFragmentManager.beginTransaction().add(defaultFragment,"DEFAULT")
//
//        }
    }
}