
fun main() {
    val firstName: String
    firstName = "Andrew"
    val lastNameString = "Nazentsev"

    var heightInt = 175
    var weight = 38f

    val isChild1 = "is a Child"
    val isChild2 = "is not a Child"
    val isChild: Boolean = (heightInt < 140 || weight < 40)
    val isChild4 = if (isChild == true) isChild1 else isChild2

    val info = """
        Firstname: ${firstName} 
        Lastname: ${lastNameString} 
        Height: ${heightInt} 
        Weight: ${weight} 
        ${isChild4}
        """
    print(info)
}