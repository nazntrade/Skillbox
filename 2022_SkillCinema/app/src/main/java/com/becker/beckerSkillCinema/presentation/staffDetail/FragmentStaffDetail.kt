package com.becker.beckerSkillCinema.presentation.staffDetail

import com.becker.beckerSkillCinema.databinding.FragmentStaffDetailBinding
import com.becker.beckerSkillCinema.presentation.ViewBindingFragment

class FragmentStaffDetail : ViewBindingFragment<FragmentStaffDetailBinding>(FragmentStaffDetailBinding::inflate) {
//    private var _binding: FragmentStaffDetailBinding? = null
//    private val binding get() = _binding!!
//
//    private val viewModel: CinemaViewModel by activityViewModels()
//    private lateinit var filmAdapter: FilmAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentStaffDetailBinding.inflate(layoutInflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val args: FragmentStaffDetailArgs by navArgs()
//        viewModel.getStaffDetail(args.staffId)
//
//        setBestFilmsAdapter()                   // Установка адаптера
//
//        setLoadingStateAndDetails()             // Отслеживание состояния загрузки
//        getStaffInfo()                          // Получение информации о персоне
//
//        setButtonsListeners()                   // Установка листенеров кнопок и надписей
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    private fun setBestFilmsAdapter() {
//        filmAdapter = FilmAdapter(20, {}, { onClickFilm(it) })
//        binding.staffDetailBestList.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        binding.staffDetailBestList.adapter = filmAdapter
//    }
//
//    private fun setLoadingStateAndDetails() {
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            viewModel.loadCurrentStaff.collect { state ->
//                when (state) {
//                    is StateLoading.Loading -> {
//                        binding.apply {
//                            progressGroup.isVisible = true
//                            loadingRefreshBtn.isVisible = false
//                            staffDetailMainGroup.isVisible = false
//                            staffDetailBestGroup.isVisible = false
//                            staffDetailFilmographyGroup.isVisible = false
//                        }
//                    }
//                    is StateLoading.Success -> {
//                        binding.apply {
//                            progressGroup.isVisible = false
//                            loadingRefreshBtn.isVisible = false
//                            staffDetailMainGroup.isVisible = true
//                            staffDetailBestGroup.isVisible = true
//                            staffDetailFilmographyGroup.isVisible = true
//                        }
//                    }
//                    else -> {
//                        binding.apply {
//                            progressGroup.isVisible = false
//                            loadingRefreshBtn.isVisible = true
//                            staffDetailMainGroup.isVisible = false
//                            staffDetailBestGroup.isVisible = false
//                            staffDetailFilmographyGroup.isVisible = false
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun getStaffInfo() {
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            viewModel.currentStaff.collect { staff ->
//                if (staff != null) {
//                    binding.apply {
//                        staffDetailPoster.loadImage(staff.posterUrl)
//                        staffDetailName.text = staff.nameRu ?: staff.nameEn ?: "Unknown name"
//                        if (staff.profession != null) staffDetailProfession.text = staff.profession
//                        else staffDetailProfession.isVisible = false
//
//                        if (staff.films != null) staffDetailFilmsCount.text =
//                            resources.getQuantityString(
//                                R.plurals.staff_details_film_count,
//                                staff.films.size,
//                                staff.films.size
//                            )
//                        if (staff.films != null) {
//                            val list: MutableList<HomeItem> = staff.films.toMutableList()
//                            list.removeAll { it.rating == null }
//                            val sortedList = list.sortedBy { it.rating?.toDouble() }.reversed()
//                            val result = mutableListOf<HomeItem>()
//
//                            if (sortedList.size > 10) {
//                                repeat(10) { result.add(sortedList[it]) }
//                            } else result.addAll(sortedList)
//
//                            result.sortedBy { it.rating }
//                            filmAdapter.submitList(result)
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun onClickFilm(filmId: Int) {
//        val action =
//            FragmentStaffDetailDirections.actionFragmentStaffDetailToFragmentFilmDetail(filmId)
//        findNavController().navigate(action)
//    }
//
//    private fun getAllFilmsByStaff() {
//        findNavController().navigate(R.id.action_fragmentStaffDetail_to_fragmentFilmography)
//    }
//
//    private fun setButtonsListeners() {
//        binding.staffDetailBackBtn.setOnClickListener { requireActivity().onBackPressed() }
//        binding.staffDetailShowAllFilmsBtn.setOnClickListener { getAllFilmsByStaff() }
//        binding.staffDetailShowAllFilmsTv.setOnClickListener { getAllFilmsByStaff() }
//        binding.staffDetailShowAllBestBtn.setOnClickListener { getAllFilmsByStaff() }
//        binding.staffDetailShowAllBestTv.setOnClickListener { getAllFilmsByStaff() }
//    }
}