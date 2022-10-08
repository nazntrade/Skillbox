package com.example.lessons15_viewpager_and_dialogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.lessons15_viewpager_and_dialogs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //создадим список
    //создадим перем.она будет типа List<OnboardingScreen> и хранить данные кот.необх отобразить в списке
    private val screens: List<OnboardingScreen> = listOf(
        OnboardingScreen(
            textRes = R.string.onboarding_text_1,
            bgColorRes = R.color.onboarding_color_1,
            drawableRes = R.drawable.onboarding_drawable_1
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_2,
            bgColorRes = R.color.onboarding_color_2,
            drawableRes = R.drawable.onboarding_drawable_2
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_3,
            bgColorRes = R.color.onboarding_color_3,
            drawableRes = R.drawable.onboarding_drawable_3
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_4,
            bgColorRes = R.color.onboarding_color_4,
            drawableRes = R.drawable.onboarding_drawable_4
        ),
        OnboardingScreen(
            textRes = R.string.onboarding_text_5,
            bgColorRes = R.color.onboarding_color_5,
            drawableRes = R.drawable.onboarding_drawable_5
        ),
    )

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = OnboardingAdapter(screens, this)
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 1 //сколько страниц до и после текущей будет в пам.

        binding.viewPager.setCurrentItem(2, false)//автоперелист.страниц
        binding.viewPager.currentItem //получ.тек.позицию
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL //направление скролов

        //чтобы отследить что польз.перелисюстраницу
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                toast("selected page = $position")//тост в экстеншенах это супер
            }
        })

        //изменить анимации переключения страниц
        binding.viewPager.setPageTransformer(object : ViewPager2.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                //не видимые страницы,когда они за пред.видим.пользователя
                //на гите много вариантов
                // https://github.com/dipanshukr/Viewpager-Transformation
                when {
                    position < -1 || position > 1 -> {
                        page.alpha = 0f //float.значения от 0 до 1. 0-полн.прозр. 1-полн.не прозр.
                    }

                    //плавная прозрачность уезжающей страницы
                    position <= 0 -> {
                        page.alpha = 1 + position
                    }
                    position <= 1 -> {
                        page.alpha = 1 - position
                    }

                }

            }

        })
    }
}