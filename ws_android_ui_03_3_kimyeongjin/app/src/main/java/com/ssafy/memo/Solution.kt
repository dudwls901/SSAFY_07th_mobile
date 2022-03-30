package com.ssafy.memo

import java.io.BufferedReader
import java.io.File
import java.io.FileReader


const val path = "C:/SSAFY/gumi06_07th_kimyeongjin/ws_android_ui_03_3_kimyeongjin/app/src/main/java/com/ssafy/memo/input.txt"
val file = File(path)
val fileReader = FileReader(file)
val br = BufferedReader(fileReader)

//dir 상하좌우
val dirRC = arrayOf(
    arrayOf(),
    arrayOf(-1,0),//상
    arrayOf(1,0),//하
    arrayOf(0,-1),//좌
    arrayOf(0,1)//우
)
data class Frog(val r: Int, val c: Int, val dir: Int)

private lateinit var frogList: Array<Frog>
private lateinit var graph: Array<BooleanArray>
var answer=0


//fun canMove(r: Int, c: Int, dir: Int, jump: Int): Boolean{
//    println("$r $c $dir $jump")
//    var nr = r
//    var nc = c
//    for(i in 1 .. jump){
//        nr += dirRC[dir][0]
//        nc += dirRC[dir][1]
//        if(graph[nr][nc])
//            return false
//    }
//    return true
//}

fun simulation(n: Int, m: Int){

    //원래 있던 개구리랑 겹치거나
    //바깥으로 나가는 경우 die, continue

    for(frog in frogList){
        //개구리마다 3번
        var (r,c,dir) = frog
//        println("$r $c $dir")
        //이동
        graph[r][c] = false
        for(jump in 3 downTo 1 step 1){
            val nr = r+dirRC[dir][0]*jump
            val nc = c+ dirRC[dir][1]*jump
            //그래프 나간 경우
            if(nr !in 0 until n || nc !in 0 until n) break
            //개구리 뛰어넘을 수 있는 경우 밟지만 않으면 됨
            if(graph[nr][nc]) break
            //개구리가 개구리를 뛰어넘지 못한다면
            //if(!canMove(r,c,dir,jump)) break

            //3번 뛰어서 살아있는 경우 그래프에 표시
            if(jump==1){
                graph[nr][nc] = true
                answer++
            }
            //현재 좌표 초기화
            r = nr
            c = nc
        }
    }
}

fun main() = with(System.out.bufferedWriter()){
    val T = br.readLine().toInt()

    for(t in 1 .. T){
        //input
        val (n,m) = br.readLine().split(' ').map{it.toInt()}
        frogList = Array(m){Frog(0,0,0)}
        graph = Array(n){ BooleanArray(n) }
        for(i in 0 until m) {
            val (r,c,dir) = br.readLine().split(' ').map{it.toInt()}
            frogList[i] = Frog(r,c,dir)
            graph[r][c] = true
        }

        //solve
        simulation(n,m)

        //output
        write("#$t $answer\n")

        //reset
        answer=0
        for(i in graph.indices){
            for(j in graph.indices){
                graph[i][j] = false
            }
        }
    }

    close()
}