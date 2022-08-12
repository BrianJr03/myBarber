package jr.brian.mybarber.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityAboutBinding
import jr.brian.mybarber.model.data.Constant.DEV_PIC_URL
import jr.brian.mybarber.model.data.Constant.GITHUB_PROFILE_URL
import jr.brian.mybarber.model.data.Constant.LINKEDIN_PROFILE_URL


class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            linkedInIcon.setOnClickListener {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(LINKEDIN_PROFILE_URL)
                )
                startActivity(webIntent)
            }
            gitHubIcon.setOnClickListener {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(GITHUB_PROFILE_URL)
                )
                startActivity(webIntent)
            }
            aboutBackArrow.setOnClickListener {
                super.onBackPressed()
                overridePendingTransition(
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
                finish()
            }
            Glide.with(this@AboutActivity)
                .load(DEV_PIC_URL)
                .into(devPic)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_in_left,
            R.anim.slide_out_left
        )
    }
}