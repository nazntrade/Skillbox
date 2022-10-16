import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import kotlin.concurrent.thread

fun main() {
    outputStreamBufferedExample()
    inputStreamExample()
}

fun basicFile() {
    val file = File("/Users/myalkin/Desktop/filename.txt")


    if(!file.exists()) {
        val result = file.createNewFile()
        println("file created = $result")
        println("file length = ${file.length()}")
        println("file abs path = ${file.absolutePath}")
        println("file name = ${file.name}")
    } else {
        val result = file.delete()
        println("file deleted = $result")
    }
}

fun basicFolder() {
    val folder = File("/Users/myalkin/Desktop/testfolder")
    folder.mkdir()

    println("file.isFile = ${folder.isFile}")
    println("file.isDirectory = ${folder.isDirectory}")
}

fun relativePathExample() {
    val folder = File("testfolder/innerfolder/exampledir")
    folder.mkdirs()
    val file = File(folder, "filename.txt")
    file.createNewFile()
    println("folder abs path = ${folder.absolutePath}")

    folder.listFiles()?.forEachIndexed { index, file ->
        println("file $index = ${file.absolutePath}")
    }
}

fun outputStreamExample() {
    val folder = File("testfolder/innerfolder/exampledir")
    folder.mkdirs()
    val file = File(folder, "filename.txt")
    file.createNewFile()
    try {
        val outputStream = file.outputStream()
        outputStream.use {
            it.write("Test string".toByteArray())
        }
    } catch (throwable: Throwable) {
        //
    }
}

fun outputStreamBufferedExample() {
    val folder = File("testfolder/innerfolder/exampledir")
    folder.mkdirs()
    val file = File(folder, "filename.txt")
    file.createNewFile()

    try {
        val start = System.currentTimeMillis()
        file.outputStream().buffered().use { stream ->
            (0..100_000).forEach {
                stream.write("$it\n".toByteArray())
            }
        }
        println("time = ${System.currentTimeMillis() - start}")
    } catch (t: Throwable) {

    }
}

fun inputStreamExample() {
    val folder = File("testfolder/innerfolder/exampledir")
    folder.mkdirs()
    val file = File(folder, "filename.txt")

    try {
        val text = file.inputStream()
                .bufferedReader()
                .use {
                    it.readText()
                }
        println(text)
    } catch (t: Throwable) {

    }

}