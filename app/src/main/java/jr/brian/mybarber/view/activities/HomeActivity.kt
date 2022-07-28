package jr.brian.mybarber.view.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GravityCompat
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityHomeBinding
import jr.brian.mybarber.model.util.replaceFragment
import jr.brian.mybarber.view.fragments.HaircutHomeFragment


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
        replaceFragment(R.id.container_home, HaircutHomeFragment())
        initFAB()
        binding.apply {
            homeTv.isSelected = true
            notificationsBtn.setOnClickListener {
                startActivity(
                    Intent(
                        this@HomeActivity,
                        NotificationsActivity::class.java
                    )
                )
            }
        }
        initDrawer()

    }

    private fun initFAB() {
        binding.apply {
            fabServices.setOnClickListener {
                startActivity(Intent(this@HomeActivity, ServiceActivity::class.java))
            }
            fabOptions.setOnClickListener {
                if (!drawerLayout.isOpen) {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
            }
            fabSignOut.setOnClickListener {
                openSignOutDialog()
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
                R.id.view_services -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    startActivity(Intent(this@HomeActivity, ServiceActivity::class.java))
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
                R.id.contact_us -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.business_hours -> {
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

    private fun signOut() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun openSignOutDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Sign Out")
            .setMessage("Do you wish to sign out?")
            .setPositiveButtonIcon(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.sign_out_10
                )
            )
            .setPositiveButton(null) { d, _ ->
                d.dismiss()
                signOut()
            }
            .setNegativeButtonIcon(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.close_10
                )
            )
            .setPositiveButton(null) { d, _ ->
                d.dismiss()
            }
            .create()
        alertDialog.setOnShowListener {
            alertDialog.findViewById<TextView?>(android.R.id.message)
                ?.apply {
                    setTextColor(getColor(R.color.blueish_idk))
                    setTypeface(null, Typeface. BOLD)
                }
        }
        alertDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(a)
    }
}