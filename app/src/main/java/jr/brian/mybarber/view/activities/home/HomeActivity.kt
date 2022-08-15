package jr.brian.mybarber.view.activities.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.createBitmap
import androidx.core.view.GravityCompat
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityHomeBinding
import jr.brian.mybarber.databinding.NavHeaderBinding
import jr.brian.mybarber.model.data.Constant.DEV_WEBSITE_URL
import jr.brian.mybarber.model.data.local.SharedPrefHelper
import jr.brian.mybarber.model.util.openDialog
import jr.brian.mybarber.model.util.replaceFragment
import jr.brian.mybarber.view.activities.AboutActivity
import jr.brian.mybarber.view.activities.BusinessHoursActivity
import jr.brian.mybarber.view.activities.MainActivity
import jr.brian.mybarber.view.activities.ShowcaseActivity
import jr.brian.mybarber.view.activities.barber.BarbersActivity
import jr.brian.mybarber.view.activities.services.ServiceActivity
import jr.brian.mybarber.view.auth_fragments.SignInFragment
import jr.brian.mybarber.view.fragments.HaircutHomeFragment
import java.io.ByteArrayOutputStream
import java.io.IOException


@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navHeaderBinding: NavHeaderBinding
    private lateinit var sharedPrefHelper: SharedPrefHelper

    private lateinit var header: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        binding.navView.inflateHeaderView(R.layout.nav_header)
        navHeaderBinding = NavHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        sharedPrefHelper = SharedPrefHelper(this)
        verifySignIn()
        getPfpFromPrefs()
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
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
            }
        }
        initDrawer()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun initFAB() {
        binding.apply {
            fabBookAppt.setOnClickListener { startBookAppt() }
            tvBookAppt.setOnClickListener { startBookAppt() }
            fabServices.setOnClickListener { startServices() }
            tvServices.setOnClickListener { startServices() }
            fabCall.setOnClickListener { startPhone() }
            tvCall.setOnClickListener { startPhone() }
            fabMaps.setOnClickListener { startMap() }
            tvMaps.setOnClickListener { startMap() }
            fabOptions.setOnClickListener { openDrawer() }
            tvOptions.setOnClickListener { openDrawer() }
            fabSignOut.setOnClickListener { signOut() }
            tvSignOut.setOnClickListener { signOut() }
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
                R.id.select_pfp -> {
                    imageChooser()
                }
                R.id.book_appt -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    if (binding.tvSignOut.text.equals("Sign In")) {
                        openDialog(
                            this@HomeActivity,
                            "Please Sign In",
                            R.drawable.info_24,
                            "You must be signed in to book an appointment"
                        )
                    } else {
                        startActivity(
                            Intent(
                                this@HomeActivity,
                                BarbersActivity::class.java
                            )
                        )
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    }
                }
                R.id.view_services -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    startActivity(
                        Intent(this, ServiceActivity::class.java)
                    )
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                }
                R.id.showcase -> {
                    startActivity(
                        Intent(
                            this@HomeActivity,
                            ShowcaseActivity::class.java
                        )
                    )
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.offers -> {
                    Toast.makeText(
                        this,
                        "Coming Soon",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.home_care_products -> {
                    Toast.makeText(
                        this,
                        "Coming Soon",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.business_hours -> {
                    startActivity(
                        Intent(
                            this@HomeActivity,
                            BusinessHoursActivity::class.java
                        )
                    )
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.user_ratings -> {
                    Toast.makeText(
                        this,
                        "Coming Soon",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.share -> {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT, "Check this website out!\n" +
                                    "The dev of this app wrote it!\n" +
                                    DEV_WEBSITE_URL
                        )
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.about -> {
                    startActivity(Intent(this@HomeActivity, AboutActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }
    }

    private fun startServices() {
        startActivity(Intent(this@HomeActivity, ServiceActivity::class.java))
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
    }

    private fun startBookAppt() {
        if (binding.tvSignOut.text.equals("Sign In")) {
            openDialog(
                this@HomeActivity,
                "Please Sign In",
                R.drawable.info_24,
                "You must be signed in to book an appointment"
            )
        } else {
            startActivity(Intent(this@HomeActivity, BarbersActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }
    }

    private fun startMap() {
        val maps = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:41.876701, -87.634903?z=11")
        )
        startActivity(maps)
    }

    private fun startPhone() {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:1234567890")
        startActivity(intent)
    }

    private fun openDrawer() {
        binding.apply {
            if (!drawerLayout.isOpen) {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun imageChooser() {
        if (binding.tvSignOut.text.equals("Sign In")) {
            openDialog(
                this,
                "Please Sign In",
                R.drawable.info_24,
                "You must be signed in to save a profile picture"
            )
        } else {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT
            launchImageActivity.launch(i)
        }
    }

    private var launchImageActivity = registerForActivityResult(
        StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode
            == RESULT_OK
        ) {
            val data = result.data
            if (data != null
                && data.data != null
            ) {
                val selectedImageUri = data.data
                var selectedImageBitmap = createBitmap(100, 100)
                try {
                    selectedImageBitmap = selectedImageUri?.let { getPfpFromGallery(it) }!!
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val baos = ByteArrayOutputStream()
                selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val b: ByteArray = baos.toByteArray()
                val encodedImage: String = Base64.encodeToString(b, Base64.DEFAULT)
                sharedPrefHelper.editor.putString("pfp", encodedImage).commit()
                getPfpFromPrefs()
            }
        }
    }

    private fun getPfpFromGallery(selectedPhotoUri: Uri): Bitmap {
        return when {
            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                this.contentResolver,
                selectedPhotoUri
            )
            else -> {
                val source = ImageDecoder.createSource(this.contentResolver, selectedPhotoUri)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }

    private fun getPfpFromPrefs() {
        header = binding.navView.getHeaderView(0)
        val pfp = header.findViewById(R.id.pfp) as AppCompatImageView
        val previouslyEncodedImage: String =
            sharedPrefHelper.encryptedSharedPrefs.getString("pfp", "").toString()
        if (!previouslyEncodedImage.equals("", ignoreCase = true)) {
            val b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
            pfp.setImageBitmap(bitmap)
        }
    }

    private fun signOut() {
        sharedPrefHelper.signOut()
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
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