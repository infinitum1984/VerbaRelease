package com.emptydev.verba.mistakes

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import com.emptydev.verba.R
import com.emptydev.verba.stringToPairArray


class MistakesDialog(val content:String):DialogFragment() {
    lateinit var mainView: View
    lateinit var  tvMistakes:TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView=inflater.inflate(R.layout.mistakes_fragment,container,false)
        tvMistakes=mainView.findViewById<TextView>(R.id.tv_mistakes)
        val btnOk=mainView.findViewById<Button>(R.id.btn_dlg_ok)
        val btnCopy= mainView.findViewById<ImageButton>(R.id.btn_dlg_copy)
        btnCopy.setOnClickListener {
            copyText();
        }
        tvMistakes.text=refractContent(content)

        btnOk.setOnClickListener {
            dismiss()
        }
        return mainView.rootView
    }

    private fun copyText() {
        val clipboard= ContextCompat.getSystemService(context!!, ClipboardManager::class.java)

        val clip = ClipData.newPlainText("Mistakes", tvMistakes.text.toString())
        clipboard!!.setPrimaryClip(clip)
        Toast.makeText(context!!,getString(R.string.text_copied),Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()

        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    fun refractContent(content: String):String{
        var out_str=""
        val map= stringToPairArray(content)

        for (it in map){
            out_str=out_str+"${it.first} - ${it.second}\n"
        }
        return out_str
    }
}
