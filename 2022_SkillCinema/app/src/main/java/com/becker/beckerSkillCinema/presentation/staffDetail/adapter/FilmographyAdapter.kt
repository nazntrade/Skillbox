package com.becker.beckerSkillCinema.presentation.staffDetail.adapter

//class FilmographyAdapter(
//    private val onFilmClick: (Int) -> Unit
//) : ListAdapter<StaffsFilms, FilmographyViewHolder>(FilmographyDiff()) {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilmographyViewHolder(
//        ItemFilmographyFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//    )
//
//    override fun onBindViewHolder(holder: FilmographyViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.binding.apply {
//            itemFilmographyImage.loadImage(item.posterUrlPreview)
//            itemFilmographyName.text = item.nameRu ?: item.nameEn
//            itemFilmographyGenre.text = PROFESSIONS[item.professionKey]
//        }
//        holder.binding.root.setOnClickListener { onFilmClick(item.filmId) }
//    }
//}