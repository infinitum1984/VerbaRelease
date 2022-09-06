package com.emptydev.verba

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment

fun Fragment.vibratePhone() {
    val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(200)
    }
}
fun arrayToString(map: List<Pair<String,String>>):String{
    var outString=""
    for (it in map){
        outString+="${it.first},${it.second}."
    }
    return outString
}
fun arrayToStringRevers(map: List<Pair<String,String>>):String{
    var outString=""
    for (it in map){
        outString+="${it.second},${it.first}."
    }
    return outString
}

fun stringToPairArray(s:String): List<Pair<String,String>>{
    var outMap= ArrayList<Pair<String,String>>()

    val pairs= s.split(".")
    Log.d("D_Util", "stringToMap: ${pairs.size}")
    for (it in pairs){
        if (it.isNotEmpty()) {
            val pair = it.split(",")
            outMap.add(Pair(pair[0],pair[1]))
            Log.d("D_Util","stringToMap: $pair");
        }
    }
    return outMap
}
fun stringToPairArrayRevers(s:String): List<Pair<String,String>>{
    var outMap= ArrayList<Pair<String,String>>()

    val pairs= s.split(".")
    Log.d("D_Util", "stringToMap: ${pairs.size}")
    for (it in pairs){
        if (it.isNotEmpty()) {
            val pair = it.split(",")
            outMap.add(Pair(pair[1],pair[0]))
            Log.d("D_Util","stringToMap: $pair");
        }
    }
    return outMap
}
fun stringToArray(s:String):List<String>{
    var cur_word=""
    var index=0;
    var outList=ArrayList<String>()
    while (index<=s.length-1){
        var symbol = s.substring(index,index+1)
        println("sym: ${symbol}")
        if (symbol.contentEquals("\n")){
            print("stringToArray: ${cur_word}");
            outList.add(cur_word.trim())
            cur_word=""

        }else{
            cur_word=cur_word+symbol
        }
        index++;
    }
    if (cur_word.isNotEmpty()){
        outList.add(cur_word)
    }
    Log.d("D_Util","stringToArray: ${outList}");
    return outList
}
fun arrayToPairStrings(map:List<Pair<String,String>>):Pair<String,String>{
    var outS1=""
    var outS2=""
    for(it in map){
        outS1+=it.first+"\n"
        outS2+=it.second+"\n"
    }
    return Pair(outS1,outS2)
}
fun appContext(activity:Activity): Context {
    return requireNotNull(activity).application
}
