package com.example.hw_ViewModelAndNavigation.petShop

class PetShopRepository {

    var animals = listOf(
        Animal.Dog(
            id = 1,
            name = "Jack",
            breed = "Terrier",
            avatarLink = "https://petlovershop.my/wp-content/uploads/2020/08/cute-puppy-body-image.jpg",
            skill = "Brings slippers in the morning"
        ),
        Animal.Bird(
            id = 2,
            name = "Lily",
            breed = "Canary",
            avatarLink = "https://moika78.ru/news2/2020/09/Snimok-ekrana-2020-09-30-v-11.31.12.png",
            song = "Melody",
            alarm = "Wakes up in 6:00 am with a beautiful melody",
            discountLink = "https://cdn.shopify.com/s/files/1/2075/1699/t/27/assets/sale-sticker_300x.png"
        ),
        Animal.Dog(
            id = 3,
            name = "Rex",
            breed = "Basset Retriever",
            avatarLink = "https://www.thesprucepets.com/thmb/kV_cfc9P4QWe-klxZ8y--awxvY4=/960x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/adorable-white-pomeranian-puppy-spitz-921029690-5c8be25d46e0fb000172effe.jpg",
            skill = "Can wash dishes"
        ),
        Animal.Dog(
            id = 4,
            name = "Hatiko",
            breed = "Husky",
            avatarLink = "https://t3.ftcdn.net/jpg/00/76/45/00/240_F_76450072_L4d6TMlp1VZ1GiGTBoeigC3j7XpS0qsC.jpg",
            skill = "Can pull heavy sleds"
        ),
        Animal.Cat(
            id = 5,
            name = "Kuzya",
            breed = "Mongrel",
            avatarLink = "https://retina.news.mail.ru/pic/a8/f2/image41654962_9fba1228514748f41eb40f1683d1090d.jpg",
        ),
        Animal.Cat(
            id = 6,
            name = "Queen",
            breed = "American Bobtail Cat",
            avatarLink = "https://aif-s3.aif.ru/images/025/372/86a0db4ddf636ee9b3edfc4a73233efa.jpg"
        ),
        Animal.Cat(
            id = 7,
            name = "Simon",
            breed = "British",
            avatarLink = "https://i.7fon.org/1000/u296606.jpg"
        )
    )

    private fun getRandomPhotoKitty(): String {
        val photoKitty = listOf(
            "https://4lapy.ru/upload/medialibrary/84c/84c48b8e8e4b57579667392f8936e5ba.jpg",
            "https://4lapy.ru/upload/medialibrary/333/3333804f3bc651dd079b2cea3137c43f.jpg",
            "https://4lapy.ru/upload/medialibrary/c28/c28954d7e723bb37b1b1cd03d2877c7f.jpg",
            "https://icdn.lenta.ru/images/2019/11/01/13/20191101130724350/pic_88f54b592eb591cd6252313b5ec3e06d.png",
            "https://4lapy.ru/upload/medialibrary/f10/f10cd0408880f408ce7b688d55e65bab.jpg",
            "https://4lapy.ru/upload/medialibrary/856/856f7a45deeb0280e4dab47901c20516.jpg",
            "https://4lapy.ru/upload/medialibrary/4cb/4cb22a00822d18f124e52e6584550a9e.jpg",
            "https://4lapy.ru/upload/medialibrary/163/163aaeb717d5a8713e8832762c4ef392.jpg",
        )
        return photoKitty.random()
    }

    fun addKitty(): Animal.Cat {
        //      animals = listOf(newAnimal) + animals
        return Animal.Cat(
            name = "",
            breed = "",
            avatarLink = getRandomPhotoKitty(),
            id = (9999..112121).random().toLong()
        )
    }

    fun deleteAnimals(animals: List<Animal>, position: Int): List<Animal> {
        return animals.filterIndexed { index, animal -> index != position }
    }
}