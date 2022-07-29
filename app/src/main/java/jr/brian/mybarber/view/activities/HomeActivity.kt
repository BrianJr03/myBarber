package jr.brian.mybarber.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GravityCompat
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityHomeBinding
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.util.replaceFragment
import jr.brian.mybarber.view.auth_fragments.SignInFragment
import jr.brian.mybarber.view.fragments.HaircutHomeFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        sharedPrefHelper = SharedPrefHelper(this)
        verifySignIn()
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

    @SuppressLint("QueryPermissionsNeeded")
    private fun initFAB() {
        binding.apply {
            fabServices.setOnClickListener {
                startActivity(Intent(this@HomeActivity, ServiceActivity::class.java))
            }
            fabCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:1234567890")
                startActivity(intent)
            }
            fabMaps.setOnClickListener {
                val maps = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:25.781620, -80.131200?z=11")
                )
                startActivity(maps)
            }
            fabOptions.setOnClickListener {
                if (!drawerLayout.isOpen) {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
            }
            fabSignOut.setOnClickListener {
                signOut()
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
                    startActivity(
                        Intent(this@HomeActivity, ServiceActivity::class.java)
                    )
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
                    startActivity(
                        Intent(
                            this@HomeActivity,
                            BusinessHoursActivity::class.java
                        )
                    )
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.user_ratings -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.share -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.about -> {
                    startActivity(Intent(this@HomeActivity, AboutActivity::class.java))
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
        sharedPrefHelper.signOut()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun verifySignIn() {
        sharedPrefHelper.encryptedSharedPrefs.apply {
            if (!(this.contains(SignInFragment.PHONE_NUM) && this.contains(SignInFragment.PASSWORD))) {
                binding.apply {
                    tvSignOut.text = getString(R.string.sign_in)
                    fabSignOut.setIconResource(R.drawable.sign_in_24)
                    fabSignOut.setOnClickListener {
                        super.onBackPressed()
                        finish()
                    }
                }
            }
        }
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