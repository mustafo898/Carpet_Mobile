package dark.composer.carpet.presentation.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
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

        binding.bottomNavigation.setupWithNavController(controller)
//        checkPermission()
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