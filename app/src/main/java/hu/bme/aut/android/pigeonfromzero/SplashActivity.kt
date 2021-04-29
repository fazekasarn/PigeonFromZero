package hu.bme.aut.android.pigeonfromzero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView
import hu.bme.aut.android.pigeonfromzero.databinding.ActivityMainBinding
import hu.bme.aut.android.pigeonfromzero.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lottieAnimation.animate().translationY((-1600).toFloat()).setDuration(1000).startDelay=2750
        Handler().postDelayed({
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()},3750)
    }
}