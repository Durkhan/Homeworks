
import java.text.Collator
import java.util.*
import kotlin.Comparator
import kotlin.collections.List


fun main(args: Array<String>) {

    val listInts:List<Int> = listOf(1, 3, 5, 4, 2, 5)
    val resultInts = uniqueOrdered(listInts)
    println(resultInts) //result[1, 2, 3, 4, 5]

    val listStrs = listOf('a', 'B', 'A', 'b', 'A', 'a')
    println(uniqueOrdered(listStrs)) //result [a, A, b, B]


}

inline fun  <reified T : Comparable<T>?>uniqueOrdered(list: List<T>):List<T>{
    //reified help to class Type
    when(T::class) {
        Integer::class -> {
            //integers for sorted
            //distinct Returns a list containing only distinct elements from the given list
            return list.distinct().sortedWith(Comparator.naturalOrder())
        }

        Char::class -> {
            //first chars to string for use Collator
            val strs= mutableListOf<String>()
            list.forEach{
                strs.add(it.toString())
            }
            // locale-sensitive String comparison
            Collections.sort(strs,Collator.getInstance(Locale.ENGLISH))
            // strings list convert to chars and cast generic type
            val chars= mutableListOf<T>()
            strs.forEach{
                chars.add(it.single() as T)
            }
            return chars.distinct()
        }
    }
    return list
}

