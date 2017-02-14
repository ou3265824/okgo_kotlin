package com.example


/**
 * Created by Administrator on 2017/2/10.
 */


fun main(array: Array<String>) {

    println("Hello World")
    val s: String
    var a = 1
    var b = 2
    var c = 3

    add(a, b)
    println(add(a, b, c))
    var arr = arrayOf(3, 2,10,1,5,4,8,6,15)
    list(arr)
    println(parse(1.012))

    var person=Person("13011","qwert")
    var (tel,pass)=person
    println("$tel")

    var map=mapOf("a" to "a1","b" to "b2")
    var list= listOf<String>()
    var  m=mapOf<String,String>()

    m["b"]
    getMap(map)

}


fun add(a: Int, b: Int) {
    if (a in 1..b) println(true) else println(false)



    return println("加法：${a + b}--" + (a + b))
}

fun add(a: Int, b: Int, c: Int) = a + b * c

/**
 * 循环输入
 */
fun list(array: Array<Int>) {
    if (array.size == 0) return
//    for (arr in array){
//        if (5 in array) println(true) else println(false)
//        println("First argument: ${arr}")
//    }
//    for (i in array.indices){
//        println("First argument: ${array[i]}")
//
//    }
//    var i=0;
//    while (i<array.size){
//        println("First argument: ${array[i++]}")
//        when(i){
//             0 -> println("when"+array[i++])
//             1 -> println("when: ${array[i++]}")
//        }
//
//    }
    /**
     * 过滤比5大的值
     */
    val position=array.filter { it>5 }
    val po=array.filter { x->x<5 }
    println(position+"--"+po)
//    print("First argument: ${array[0]}")
}

/**
 * 判断
 */
fun max(a: Int, b: Int): Int {
    if (a > b)
        return a
    else
        return b

}

fun parse(a:Any):Int{
    if (a is String)
        return a.length
    else if (a is Int)
        return 0
    return 10
}

/**
 * 遍历map
 */
fun getMap(map: Map<String,String>){
    for ((k,v) in map){
        println("$k->$v--${map["a"]}")
    }
}
