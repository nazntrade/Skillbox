package ru.zhdanon.skillcinema.ui.intro

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ru.zhdanon.skillcinema.R
import ru.zhdanon.skillcinema.data.OnBoardingResources
import ru.zhdanon.skillcinema.databinding.IntroFragmentBinding

@AndroidEntryPoint
class IntroFragment : Fragment() {

    private var _binding: IntroFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: IntroPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = IntroFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listIntro = listOf(
            OnBoardingResources(
                message = resources.getString(R.string.intro_first_description),
                imageId = R.drawable.wfh_2
            ),
            OnBoardingResources(
                message = resources.getString(R.string.intro_description_second),
                imageId = R.drawable.wfh_4_1
            ), OnBoardingResources(
                message = resources.getString(R.string.intro_description_third),
                imageId = R.drawable.wfh_8
            )
        )

        adapter = IntroPager(listIntro)
        binding.introViewpager.adapter = adapter
        TabLayoutMediator(binding.tab, binding.introViewpager) { _, _ -> }.attach()

        binding.btnIntroSkip.setOnClickListener { skipIntroClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        findNavController().clearBackStack(R.id.introFragment)
        _binding = null
    }

    private fun skipIntroClick() {
        PreferenceManager.getDefaultSharedPreferences(context).edit().apply {
            putBoolean(PREFERENCES_NAME, true)
            apply()
        }
        findNavController().navigate(R.id.action_introFragment_to_mainFragment)
    }

    companion object {
        const val PREFERENCES_NAME = "pref_name"
    }
}