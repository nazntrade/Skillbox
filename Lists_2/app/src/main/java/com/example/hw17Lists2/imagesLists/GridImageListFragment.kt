package com.example.hw17Lists2.imagesLists

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hw17Lists2.R
import com.example.hw17Lists2.databinding.FragmentImageListBinding

class GridImageListFragment : Fragment(R.layout.fragment_image_list) {

    private val images = listOf(
        "https://cdn.fishki.net/upload/post/201310/29/1212586/ab48385eb9055103008b7487e7a_prev.jpg",
        "https://cdn.fishki.net/upload/post/201310/29/1212586/0_d46b5_f03344f5_L.jpg",
        "https://cdn.fishki.net/upload/post/201310/29/1212586/ecd20af30ec986975c74ba4005f_prev.jpg",
        "https://cdn.fishki.net/upload/post/201310/29/1212586/e7ec7af02f715b2671864b9eca8_prev.jpg",
        "https://cdn.fishki.net/upload/post/201310/29/1212586/dae7fa24d8af37477922a8ede77.jpg",
        "https://cdn.fishki.net/upload/post/201310/29/1212586/1373805511_3733555-r3l8t8d-650-enhanced-buzz-wide-6555.jpg",
        "https://cdn.fishki.net/upload/post/201310/29/1212586/18c9de6610c0ba9de58b9b1f69f.jpg",
        "https://cdn.fishki.net/upload/post/201310/29/1212586/6a0c1dfa91501b74d4e6da7486d.jpg",
        "https://cdn.fishki.net/upload/post/201310/29/1212586/0_d46c2_5c08ad4a_L.jpg",
        "https://cdn.fishki.net/upload/post/201310/29/1212586/0_d46bc_d9f2169c_L.jpg",
        "https://cdn.fishki.net/upload/post/201310/29/1212586/0_d46bb_78c5997c_L.jpg",
        "https://cdn.vox-cdn.com/thumbor/0WPcZHYeASt9vcyLSTAbS2aV_L0=/0x0:1920x1080/920x0/filters:focal(0x0:1920x1080):format(webp):no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/20048009/looney_tunes_cartoons_9.jpg",
        "https://cdn.vox-cdn.com/thumbor/SO0qvEIMPDcEKRb667omFPPPWcE=/0x0:1920x1080/920x0/filters:focal(0x0:1920x1080):format(webp):no_upscale()/cdn.vox-cdn.com/uploads/chorus_asset/file/20048012/looney_tunes_cartoons_20.jpg",
        "https://4.bp.blogspot.com/-rKW3mU4Y2lc/Vo6JidxbmmI/AAAAAAAABC4/KHAKYTpr5us/s1600/The%2BLooney%2BTunes%2BShow%2B-%2B%2BShe%2527s%2BA%2BCrazy%2BPerson%2B%2528Lola%2BBunny%2529%2B%25281%2529.gif",
        "https://icdn.lenta.ru/images/2021/04/27/16/20210427163138131/detail_9b31eaf4376cdff03e0ba1bcaa826a01.jpg",
        "https://mirpozitiva.ru/wp-content/uploads/2019/11/1472042660_10.jpg"
    )

    lateinit var binding: FragmentImageListBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImageListBinding.bind(view)
        initList()
    }

    private fun initList() = with(binding.imageList) {
        adapter = ImageAdapter().apply {
            setImages(images.shuffled() + images + images)
        }

        setHasFixedSize(true)

        addItemDecoration(ItemDecoration(requireContext()))

        layoutManager = GridLayoutManager(requireContext(), 3)
    }
}