package jr.brian.mybarber.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import jr.brian.mybarber.R
import jr.brian.mybarber.databinding.ActivityMainBinding
import jr.brian.mybarber.model.util.replaceFragment
import jr.brian.mybarber.model.util.startHomeActivity
import jr.brian.mybarber.view.auth_fragments.SignInFragment
import jr.brian.mybarber.view.auth_fragments.SignUpFragment

class MainActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        replaceFragment(R.id.container, SignInFragment())
        binding.apply {
            skip.setOnClickListener {
                startHomeActivity(this@MainActivity, this@MainActivity)
            }
        }
    }

    override fun onCheckedChanged(group: RadioGroup, checkId: Int) {
        val checkRadioButton = group.findViewById<RadioButton>(group.checkedRadioButtonId)
        checkRadioButton?.let {
            when (checkRadioButton.id) {
                R.id.addr_rb1 -> replaceFragment(R.id.container, SignInFragment())
                else -> replaceFragment(R.id.container, SignUpFragment())
            }
        }
    }

    private fun initListeners() {
        val group = findViewById<RadioGroup>(R.id.radio_group)
        group.setOnCheckedChangeListener(this)
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(a)
    }
}