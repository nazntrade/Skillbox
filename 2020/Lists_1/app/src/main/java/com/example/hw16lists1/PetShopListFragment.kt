package com.example.hw16lists1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw16lists1.databinding.FragmentPetShopListBinding
import android.widget.EditText

class PetShopListFragment : Fragment(R.layout.fragment_pet_shop_list) {

    private val photoKitty = listOf(
        "https://4lapy.ru/upload/medialibrary/84c/84c48b8e8e4b57579667392f8936e5ba.jpg",
        "https://4lapy.ru/upload/medialibrary/333/3333804f3bc651dd079b2cea3137c43f.jpg",
        "https://4lapy.ru/upload/medialibrary/c28/c28954d7e723bb37b1b1cd03d2877c7f.jpg",
        "https://icdn.lenta.ru/images/2019/11/01/13/20191101130724350/pic_88f54b592eb591cd6252313b5ec3e06d.png",
        "https://4lapy.ru/upload/medialibrary/f10/f10cd0408880f408ce7b688d55e65bab.jpg",
        "https://4lapy.ru/upload/medialibrary/856/856f7a45deeb0280e4dab47901c20516.jpg",
        "https://4lapy.ru/upload/medialibrary/4cb/4cb22a00822d18f124e52e6584550a9e.jpg",
        "https://4lapy.ru/upload/medialibrary/163/163aaeb717d5a8713e8832762c4ef392.jpg",
    )

    private var animals = listOf(
        Animal.Dog(
            name = "Jack",
            breed = "Terrier",
            avatarLink = "https://petlovershop.my/wp-content/uploads/2020/08/cute-puppy-body-image.jpg",
            skill = "Brings slippers in the morning"
        ),
        Animal.Bird(
            name = "Lily",
            breed = "Canary",
            avatarLink = "https://t1.ea.ltmcdn.com/en/images/5/9/5/img_caring_for_a_canary_595_600.jpg",
            song = "Melody",
            alarm = "Wakes up in 6:00 am with a beautiful melody",
            discountLink = "https://previews.123rf.com/images/msvectorplus/msvectorplus1710/msvectorplus171000110/88259461-special-offer-30-discount-on-the-goods-vector-red-icon-.jpg"
        ),
        Animal.Dog(
            name = "Rex",
            breed = "Basset Retriever",
            avatarLink = "https://www.thesprucepets.com/thmb/kV_cfc9P4QWe-klxZ8y--awxvY4=/960x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/adorable-white-pomeranian-puppy-spitz-921029690-5c8be25d46e0fb000172effe.jpg",
            skill = "Can wash dishes"
        ),
        Animal.Dog(
            name = "Hatiko",
            breed = "Husky",
            avatarLink = "https://t3.ftcdn.net/jpg/00/76/45/00/240_F_76450072_L4d6TMlp1VZ1GiGTBoeigC3j7XpS0qsC.jpg",
            skill = "Can pull heavy sleds"
        ),
        Animal.Cat(
            name = "Kuzya",
            breed = "Mongrel",
            avatarLink = "https://retina.news.mail.ru/pic/a8/f2/image41654962_9fba1228514748f41eb40f1683d1090d.jpg",
        ),
        Animal.Cat(
            name = "Queen",
            breed = "American Bobtail Cat",
            avatarLink = "https://aif-s3.aif.ru/images/025/372/86a0db4ddf636ee9b3edfc4a73233efa.jpg"
        ),
        Animal.Cat(
            name = "Simon",
            breed = "British",
            avatarLink = "https://i.7fon.org/1000/u296606.jpg"
        )
    )

    private var petShopAdapter: PetShopAdapter? = null

    private lateinit var binding: FragmentPetShopListBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPetShopListBinding.bind(view)
        initList()

        binding.addFab.setOnClickListener {
            addNewKittyWithDialogWindow()
        }

        petShopAdapter?.updateAnimals(animals)
        petShopAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        petShopAdapter = null
    }

    private fun initList() {

        petShopAdapter = PetShopAdapter { position -> deleteAnimals(position) }
        with(binding.petList) {
            adapter = petShopAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun deleteAnimals(position: Int) {
        animals = animals.filterIndexed { index, animal -> index != position }
        if (animals.isEmpty()) {
            binding.emptyTextView.isGone = false
            "List empty".also { binding.emptyTextView.text = it }
        }
        petShopAdapter?.updateAnimals(animals)
        petShopAdapter?.notifyItemRemoved(position)
    }

    private fun addNewKittyWithDialogWindow() {
        val view = layoutInflater.inflate(R.layout.dialog_add_kitty, null)
        val dialogNameTextView = view.findViewById<EditText>(R.id.dialogNameTextView)
        val dialogBreedTextView = view.findViewById<EditText>(R.id.dialogBreedTextView)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        builder.setPositiveButton("Ok") { _, _ ->
            Log.d("aaaa", "setPositiveButton")
            val newAnimal = Animal.Cat(
                name = dialogNameTextView.text.toString(),
                breed = dialogBreedTextView.text.toString(),
                avatarLink = photoKitty.random()
            )
            animals = listOf(newAnimal) + animals
            petShopAdapter?.updateAnimals(animals)
            petShopAdapter?.notifyItemInserted(0)
            binding.petList.scrollToPosition(0)
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }
}