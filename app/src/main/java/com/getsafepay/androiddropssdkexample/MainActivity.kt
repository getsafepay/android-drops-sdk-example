package com.getsafepay.androiddropssdkexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.getsafepay.androiddropssdk.SafepayEnvironment
import com.getsafepay.androiddropssdkexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDefaults()
        setupEnvironmentDropdown()
        binding.openDemoButton.setOnClickListener { openDemo() }
    }

    private fun setupDefaults() {
        binding.environmentInput.setText(SafepayEnvironment.SANDBOX.name.lowercase(), false)
        binding.countryInput.setText("PK")
        binding.cityInput.setText("Karachi")
        binding.streetInput.setText("123 Main Street")
    }

    private fun setupEnvironmentDropdown() {
        val items = SafepayEnvironment.entries.map { it.name.lowercase() }.toTypedArray()
        binding.environmentInput.setSimpleItems(items)
    }

    private fun openDemo() {
        val authToken = binding.authTokenInput.text?.toString()?.trim().orEmpty()
        val tracker = binding.trackerInput.text?.toString()?.trim().orEmpty()
        val ddcJwt = binding.ddcJwtInput.text?.toString()?.trim().orEmpty()
        val ddcUrl = binding.ddcUrlInput.text?.toString()?.trim().orEmpty()

        if (authToken.isEmpty() || tracker.isEmpty() || ddcJwt.isEmpty() || ddcUrl.isEmpty()) {
            binding.errorText.text = "Auth token, tracker, DDC JWT, and DDC URL are required."
            binding.errorText.visibility = android.view.View.VISIBLE
            return
        }

        binding.errorText.visibility = android.view.View.GONE

        startActivity(
            Intent(this, PayerAuthenticationActivity::class.java).apply {
                putExtra(EXTRA_ENVIRONMENT, binding.environmentInput.text?.toString()?.trim().orEmpty())
                putExtra(EXTRA_AUTH_TOKEN, authToken)
                putExtra(EXTRA_TRACKER, tracker)
                putExtra(EXTRA_DDC_JWT, ddcJwt)
                putExtra(EXTRA_DDC_URL, ddcUrl)
                putExtra(EXTRA_STREET, binding.streetInput.text?.toString()?.trim().orEmpty())
                putExtra(EXTRA_CITY, binding.cityInput.text?.toString()?.trim().orEmpty())
                putExtra(EXTRA_COUNTRY, binding.countryInput.text?.toString()?.trim().orEmpty())
                putExtra(EXTRA_DO_CAPTURE, binding.doCaptureSwitch.isChecked)
                putExtra(EXTRA_DO_CARD_ON_FILE, binding.doCardOnFileSwitch.isChecked)
            },
        )
    }

    companion object {
        const val EXTRA_ENVIRONMENT = "environment"
        const val EXTRA_AUTH_TOKEN = "authToken"
        const val EXTRA_TRACKER = "tracker"
        const val EXTRA_DDC_JWT = "ddcJwt"
        const val EXTRA_DDC_URL = "ddcUrl"
        const val EXTRA_STREET = "street"
        const val EXTRA_CITY = "city"
        const val EXTRA_COUNTRY = "country"
        const val EXTRA_DO_CAPTURE = "doCapture"
        const val EXTRA_DO_CARD_ON_FILE = "doCardOnFile"
    }
}
