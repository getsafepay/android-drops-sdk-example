package com.getsafepay.androiddropssdkexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.getsafepay.androiddropssdk.AuthorizationOptions
import com.getsafepay.androiddropssdk.BillingAddress
import com.getsafepay.androiddropssdk.PayerAuthenticationData
import com.getsafepay.androiddropssdk.PayerAuthenticationErrorData
import com.getsafepay.androiddropssdk.PayerAuthenticationSuccessData
import com.getsafepay.androiddropssdk.SafepayEnvironment
import com.getsafepay.androiddropssdk.SafepayErrorData
import com.getsafepay.androiddropssdk.SafepayPayerAuthenticationConfiguration
import com.getsafepay.androiddropssdk.SafepayPayerAuthenticationListener
import com.getsafepay.androiddropssdk.SafepayPayerAuthenticationView
import com.getsafepay.androiddropssdkexample.databinding.ActivityPayerAuthenticationBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PayerAuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayerAuthenticationBinding
    private lateinit var payerAuthView: SafepayPayerAuthenticationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayerAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        payerAuthView = SafepayPayerAuthenticationView(this).apply {
            listener = object : SafepayPayerAuthenticationListener {
                override fun onPayerAuthenticationSuccess(data: PayerAuthenticationSuccessData) {
                    appendLog("success", data.toString())
                }

                override fun onPayerAuthenticationFailure(data: PayerAuthenticationErrorData) {
                    appendLog("failure", data.toString())
                }

                override fun onPayerAuthenticationRequired(data: PayerAuthenticationData) {
                    appendLog("required", data.toString())
                }

                override fun onPayerAuthenticationFrictionless(data: PayerAuthenticationData) {
                    appendLog("frictionless", data.toString())
                }

                override fun onPayerAuthenticationUnavailable(data: PayerAuthenticationData) {
                    appendLog("unavailable", data.toString())
                }

                override fun onSafepayError(error: SafepayErrorData) {
                    appendLog("safepay-error", error.toString())
                }
            }
        }

        binding.payerAuthContainer.addView(payerAuthView)
        payerAuthView.configure(buildConfiguration())
    }

    override fun onDestroy() {
        payerAuthView.dispose()
        super.onDestroy()
    }

    private fun buildConfiguration(): SafepayPayerAuthenticationConfiguration {
        val environment = when (intent.getStringExtra(MainActivity.EXTRA_ENVIRONMENT)?.lowercase()) {
            "local" -> SafepayEnvironment.LOCAL
            "development" -> SafepayEnvironment.DEVELOPMENT
            "production" -> SafepayEnvironment.PRODUCTION
            else -> SafepayEnvironment.SANDBOX
        }

        return SafepayPayerAuthenticationConfiguration(
            environment = environment,
            authToken = intent.getStringExtra(MainActivity.EXTRA_AUTH_TOKEN).orEmpty(),
            tracker = intent.getStringExtra(MainActivity.EXTRA_TRACKER).orEmpty(),
            deviceDataCollectionJWT = intent.getStringExtra(MainActivity.EXTRA_DDC_JWT).orEmpty(),
            deviceDataCollectionURL = intent.getStringExtra(MainActivity.EXTRA_DDC_URL).orEmpty(),
            billing = run {
                val street = intent.getStringExtra(MainActivity.EXTRA_STREET).orEmpty()
                val city = intent.getStringExtra(MainActivity.EXTRA_CITY).orEmpty()
                val country = intent.getStringExtra(MainActivity.EXTRA_COUNTRY).orEmpty()
                if (street.isBlank() && city.isBlank() && country.isBlank()) null
                else BillingAddress(street1 = street, city = city, country = country)
            },
            authorizationOptions = AuthorizationOptions(
                doCapture = intent.getBooleanExtra(MainActivity.EXTRA_DO_CAPTURE, false),
                doCardOnFile = intent.getBooleanExtra(MainActivity.EXTRA_DO_CARD_ON_FILE, false),
            ),
        )
    }

    private fun appendLog(kind: String, message: String) {
        val timestamp = SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())
        val current = binding.eventLogText.text?.toString().orEmpty()
        val line = "[$timestamp] $kind: $message"
        binding.eventLogText.text = if (current.isBlank()) line else "$line\n\n$current"
    }
}
