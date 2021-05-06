package hu.bme.aut.android.pigeonfromzero

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import hu.bme.aut.android.pigeonfromzero.databinding.ActivityMainBinding
import hu.bme.aut.android.pigeonfromzero.fragments.HomeFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    val PREF_NAME: String = "MySettings"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toggle dolgok
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //switch kikeresés TODO: megkérdezni, hogy ez így gond-e
        val menuItem = binding.navView.menu.findItem(R.id.nav_switch)
        val switch = menuItem.actionView.findViewById(R.id.switch_id) as SwitchCompat

        //sharedpreferences alapján switch beállítása
        val sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val nightMode : Boolean = sp.getBoolean("nightMode", false)
        if (nightMode) {
            switch.isChecked = nightMode
        } else {
            switch.isChecked = nightMode
        }

        //switch kapcsolásakor nightmode váltás és sharedpreferences írás
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            val sp: SharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sp.edit()
            editor.putBoolean("nightMode", isChecked)
            editor.apply()
        }

        //navigation drawer elemek listenerjének beállítása
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    findNavController(R.id.naviHostFragment).navigate(HomeFragmentDirections.actionHomeGlobal())
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START)
                    }
                }
            }
            true
        }

    }

    //actionbarhoz kéne, de miért megy nélküle is?
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //ha navigationdrawer nyitva, akkor azt nyukja be
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
