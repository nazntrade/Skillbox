package com.example.hw16lists1

import androidx.fragment.app.Fragment

class PetShopListFragment : Fragment(R.layout.fragment_pet_shop_list) {

    private var animals = listOf(
        Animal.Dog(
            name = "Jack",
            breed = "terrier",
            avatarLink = "https://petlovershop.my/wp-content/uploads/2020/08/cute-puppy-body-image.jpg"
        ),
        Animal.Dog(
            name = "Rex",
            breed = "Basset Retriever",
            avatarLink = "https://www.thesprucepets.com/thmb/kV_cfc9P4QWe-klxZ8y--awxvY4=/960x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/adorable-white-pomeranian-puppy-spitz-921029690-5c8be25d46e0fb000172effe.jpg"
        ),
        Animal.Dog(
            name = "Hatiko",
            breed = "Husky",
            avatarLink = "https://t3.ftcdn.net/jpg/00/76/45/00/240_F_76450072_L4d6TMlp1VZ1GiGTBoeigC3j7XpS0qsC.jpg"
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
        ),
        Animal.Bird(
            name = "Lily",
            breed = "Canary",
            avatarLink = "https://t1.ea.ltmcdn.com/en/images/5/9/5/img_caring_for_a_canary_595_600.jpg",
            song = "Melody",
            funAlarm = "Wakes up in the morning",
            discountLink = "https://viva.net.ru/images/2018/12/01/50.png"
        )
    )

}