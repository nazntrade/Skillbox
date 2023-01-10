package com.becker.beckerSkillCinema.presentation.allStaffByFilm

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.becker.beckerSkillCinema.databinding.FragmentStaffAllByFilmBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment

class FragmentAllStaffsByFilm : ViewBindingFragment<FragmentStaffAllByFilmBinding>(FragmentStaffAllByFilmBinding::inflate) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


//
//    private val viewModel: CinemaViewModel by activityViewModels()
//    private lateinit var adapter: StaffAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentStaffAllByFilmBinding.inflate(layoutInflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val args: FragmentAllStaffsByFilmArgs by navArgs()
//
//        setStaffList(args.professionKey)
//        binding.allStaffsBackBtn.setOnClickListener { requireActivity().onBackPressed() }
//    }
//
//    private fun setStaffList(professionKey: String) {
//        adapter = StaffAdapter { onItemClick(it) }
//        binding.allStaffsList.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        binding.allStaffsList.adapter = adapter
//
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            when (professionKey) {
//                "ACTOR" -> {
//                    binding.allStaffsCategoryTv.text =
//                        resources.getString(R.string.label_film_actors)
//                    viewModel.currentFilmActors.collect { actorList ->
//                        adapter.submitList(actorList)
//                    }
//                }
//                else -> {
//                    binding.allStaffsCategoryTv.text =
//                        resources.getString(R.string.label_film_makers)
//                    viewModel.currentFilmMakers.collect { makersList ->
//                        adapter.submitList(makersList)
//                    }
//                }
//            }
//        }
//    }
//
//    private fun onItemClick(staff: ResponseStaffByFilmId) {
//        val action = FragmentAllStaffsByFilmDirections
//            .actionFragmentAllStaffsByFilmToFragmentStaffDetail(staff.staffId)
//        findNavController().navigate(action)
//    }
}