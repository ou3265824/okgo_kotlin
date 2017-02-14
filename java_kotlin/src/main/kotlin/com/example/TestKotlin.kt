package com.example

/**
 * Created by Administrator on 2017/2/13.
 */

fun main(array: Array<String>){
//    at()
//    eash()
    list()
}

/**
 * break@标签跳出整个循环，不加@只跳出当前循环
 * continue@只跳出当前循环
 */
fun at(){
    loop@for(i in 0..5){
        println(i)
        for (j in 20..25){
            println(j)
//            if (i==2)break@loop
//            if (i==2)break
            if (i==2)continue@loop

        }
    }
    println("结束")
}


/**
 * @forEach
 * lit@跳出当前循环
 *  只跳出当前循环，然后继续循环
 */
fun eash(){
    var list= arrayOf("a","b","c","d")
//    list.forEach lit@{
//        if (it == "c")return@lit
//        println(it)
//    }
//    list.forEach {
//        if (it == "c")return@forEach
//        println(it)
//    }
    list.forEach (fun(value : String) {
        if (value == "c")return
        println(value)
        })
}



fun list(){
    val list= mutableListOf(1,2,3,4,5)
    list.swap(2,3)
    for (l in list){
        println(l)
    }
}


fun MutableList<Int>.swap(x: Int, y: Int) {
    val temp = this[x] // this 对应 list
    this[x] = this[y]
    this[y] = temp
}


