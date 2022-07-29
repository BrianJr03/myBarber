package jr.brian.mybarber.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import jr.brian.mybarber.databinding.ActivityAboutBinding
import jr.brian.mybarber.model.data.Constant.DEV_PIC_URL


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
            websiteUrl.setOnClickListener {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://brianjr03.github.io"))
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