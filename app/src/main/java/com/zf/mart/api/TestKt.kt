package com.zf.mart.api

import kotlinx.coroutines.*


fun main(args: Array<String>) {


//    initCoroutines()

//    init2()

//    init3()

//    init4()

//    init5()

//    init6()

//    init7()


    val a = 2%2
    println(a)

}

/** 取消协程的运行 */
fun init7()= runBlocking {
    val job = launch {
        repeat(100){
            println("i am $it")
            delay(500L)
        }
    }
    delay(1300L)
    println("tired of waiting")
//    job.cancel()
//    job.join()
    println("i can quit")

}

fun init6()= runBlocking {
    GlobalScope.launch {
        repeat(1000){
            println("is$it")
            delay(500L)
        }
    }
    delay(3000L)
}


fun init5() = runBlocking {

    repeat(100000) {
        launch {
            delay(2000L)
            println(".$it")
        }
    }

}

/**
 * 提取函数重构
 */
fun init4() = runBlocking {
    launch {
        dowWorld()
    }
    println("hello")
}

/** 挂起函数 */
suspend fun dowWorld() {
    delay(1000L)
    println("world")
}


/**
 *  作用域构建器
除了由不同的构建器提供协程作用域之外，
还可以使用 coroutineScope 构建器声明自己的作用域。
它会创建新的协程作用域并且在所有已启动子协程执行完毕之前不会结束。
runBlocking 与 coroutineScope 的主要区别在于后者在等待所有子协程执行完毕时不会阻塞当前线程。
 */
fun init3() =
        runBlocking {
            launch {
                delay(200L)
                println("task from run block")
            }

            /** 等待所有子协程执行完毕时不会阻塞当前线程 */
            coroutineScope {
                launch {
                    delay(500L)
                    println("task from nest Scope")
                }
                delay(100L)
                println("task from scope")
            }

            println("Coroutine over")
        }

/**
 * 结构化的并发
 *
 */
fun init2() =
        runBlocking {
            launch {
                delay(1000L)
                println("world")
            }
            println("hello,")
        }


fun initCoroutines() {

    //顶层协程
    val job =
            GlobalScope.launch {
                delay(1000L) //非阻塞
                println("world")
            }

    println("hello,")

    runBlocking {
        //        delay(2000L)
        job.join()
    }

}