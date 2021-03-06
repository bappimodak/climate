package com.example.climate.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.climate.R
import com.example.climate.utils.PermissionHelper


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        addFragmentOnTop(HomeFragment.newInstance())
        checkForLocationPermissions()
    }

    private fun checkForLocationPermissions() {
        PermissionHelper.checkMyPermission(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            LOCATION_PERMISSION_REQUESTCODE
        )

    }

    override fun onBackPressed() {
        val fragments = supportFragmentManager
        val currentFragment = fragments.findFragmentById(R.id.container)
        if (currentFragment is HomeFragment) {
            if(currentFragment.canGoBack()){
                finish()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUESTCODE) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("HomeActivity", "Permission Granted")
            } else {
                checkForLocationPermissions()
            }
        }
    }

    /**
     * Add a fragment on top of the current stack
     */
    fun addFragmentOnTop(fragment: Fragment?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment!!)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.help){
            addFragmentOnTop(HelpFragment.newInstance())
            return true
        }
        return super.onOptionsItemSelected(item);
    }


    companion object {
        const val LOCATION_PERMISSION_REQUESTCODE = 1000
    }

}