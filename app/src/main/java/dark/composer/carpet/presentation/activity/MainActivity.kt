package dark.composer.carpet.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
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

        if (shared.getRole() == "ADMIN") {
//            startActivityFromFragment(AdminFragment(),Intent(this, MainActivity::class.java))
            supportFragmentManager.beginTransaction().add(adminFragment,"ADMIN")

        } else {
//            startActivityFromFragment(DefaultFragment(),Intent(this, MainActivity::class.java))
            supportFragmentManager.beginTransaction().add(defaultFragment,"DEFAULT")

        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        checkPermission()
//    }
//
//    private fun checkPermission() {
//        val permission = arrayOf(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//
//        if (ContextCompat.checkSelfPermission(
//                this.applicationContext,
//                permission[0]
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ContextCompat.checkSelfPermission(
//                this.applicationContext,
//                permission[1]
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ContextCompat.checkSelfPermission(
//                this.applicationContext,
//                permission[2]
//            ) == PackageManager.PERMISSION_GRANTED
//        ){
//            Log.d("SSSSS", "checkPermission: Otdi")
//        } else{
//            ActivityCompat.requestPermissions(this@MainActivity,permission,1)
//        }
//    }

}