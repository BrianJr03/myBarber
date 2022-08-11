package jr.brian.mybarber.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import jr.brian.mybarber.databinding.ActivityAboutBinding
import jr.brian.mybarber.model.data.Constant.DEV_PIC_URL
import jr.brian.mybarber.model.data.Constant.DEV_WEBSITE_URL
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
            linkedInUrl.setOnClickListener {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(LINKEDIN_PROFILE_URL))
                startActivity(webIntent)
            }
            websiteUrl.setOnClickListener {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(DEV_WEBSITE_URL))
                startActivity(webIntent)
            }
            aboutBackArrow.setOnClickListener {
                super.onBackPressed()
                finish()
            }
            Glide.with(this@AboutActivity)
                .load(DEV_PIC_URL)
                .into(devPic)
        }
    }
}