package ru.zhdanon.skillcinema.ui.allstaffbyfilm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ru.zhdanon.skillcinema.R
import ru.zhdanon.skillcinema.data.staffbyfilmid.ResponseStaffByFilmId
import ru.zhdanon.skillcinema.databinding.FragmentStaffAllByFilmBinding
import ru.zhdanon.skillcinema.ui.CinemaViewModel
import ru.zhdanon.skillcinema.ui.filmdetail.staffadapter.StaffAdapter

class FragmentAllStaffsByFilm : Fragment() {
    private var _binding: FragmentStaffAllByFilmBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CinemaViewModel by activityViewModels()
    private lateinit var adapter: StaffAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStaffAllByFilmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: FragmentAllStaffsByFilmArgs by navArgs()

        setStaffList(args.professionKey)
        binding.allStaffsBackBtn.setOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setStaffList(professionKey: String) {
        adapter = StaffAdapter { onItemClick(it) }
        binding.allStaffsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.allStaffsList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            when (professionKey) {
                "ACTOR" -> {
                    binding.allStaffsCategoryTv.text =
                        resources.getString(R.string.label_film_actors)
                    viewModel.currentFilmActors.collect { actorList ->
                        adapter.submitList(actorList)
                    }
                }
                else -> {
                    binding.allStaffsCategoryTv.text =
                        resources.getString(R.string.label_film_makers)
                    viewModel.currentFilmMakers.collect { makersList ->
                        adapter.submitList(makersList)
                    }
                }
            }
        }
    }

    private fun onItemClick(staff: ResponseStaffByFilmId) {
        val action = FragmentAllStaffsByFilmDirections
            .actionFragmentAllStaffsByFilmToFragmentStaffDetail(staff.staffId)
        findNavController().navigate(action)
    }
}