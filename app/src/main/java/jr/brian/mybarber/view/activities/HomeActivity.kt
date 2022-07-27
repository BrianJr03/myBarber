package jr.brian.mybarber.view.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initFAB()
        initDrawer()
    }

    private fun initFAB() {
        binding.apply {
            fabMain.setOnClickListener {
                if (!fabGroup.isVisible) {
                    fabBookAppt.show()
                    fabServices.show()
                    fabGroup.visibility = View.VISIBLE
                    fabMain.setIconResource(R.drawable.arrow_down_24)
                } else {
                    fabBookAppt.hide()
                    fabServices.hide()
                    fabGroup.visibility = View.GONE
                    fabMain.setIconResource(R.drawable.arrow_up_24)
                }
            }
        }
    }

    private fun initDrawer() {
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open, R.string.close
        )
        binding.apply {
            drawerLayout.addDrawerListener(toggle)
            menu.setOnClickListener {
                if (fabGroup.isVisible) {
                    fabBookAppt.hide()
                    fabServices.hide()
                    fabGroup.visibility = View.GONE
                    fabMain.setIconResource(R.drawable.arrow_up_24)
                }
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.book_appt -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.our_services -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.showcase -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.offers -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.home_care_products -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.how_to_reach -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.working_hours -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.user_ratings -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.share -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.about -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }
        initDrawerHeader()
    }

    private fun initDrawerHeader() {
        val navView = binding.navView.inflateHeaderView(R.layout.nav_header)
        val pfp = navView.findViewById<AppCompatImageView>(R.id.pfp)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}