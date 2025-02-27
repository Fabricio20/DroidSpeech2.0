package github.com.vikramezhil.droidkotspeechexample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import github.com.vikramezhil.dks.speech.Dks
import github.com.vikramezhil.dks.speech.DksListener

/**
 * Droid Kotlin Speech Example Activity
 * @author vikramezhil
 */

class MainKotlinActivity : AppCompatActivity() {

    private lateinit var dks: Dks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        dks = Dks(application, supportFragmentManager, object: DksListener {
            override fun onDksLiveSpeechResult(liveSpeechResult: String) {
                Log.d(application.packageName, "Speech result - $liveSpeechResult")
            }

            override fun onDksFinalSpeechResult(speechResult: String) {
                Log.d(application.packageName, "Final speech result - $speechResult")
            }

            override fun onDksLiveSpeechFrequency(frequency: Float) {}

            override fun onDksLanguagesAvailable(defaultLanguage: String?, supportedLanguages: ArrayList<String>?) {
                Log.d(application.packageName, "defaultLanguage - $defaultLanguage")
                Log.d(application.packageName, "supportedLanguages - $supportedLanguages")

                if (supportedLanguages != null && supportedLanguages.contains("en-IN")) {
                    // Setting the speech recognition language to english india if found
                    dks.currentSpeechLanguage = "en-IN"
                }
            }

            override fun onDksSpeechError(errMsg: String) {
                Toast.makeText(application, errMsg, Toast.LENGTH_SHORT).show()
            }
        })

        dks.injectProgressView(R.layout.layout_pv_inject)
        dks.oneStepResultVerify = true

        val view: Button = findViewById(R.id.btn_start_dks)
        view.setOnClickListener {
            dks.startSpeechRecognition()
        }
    }
}
