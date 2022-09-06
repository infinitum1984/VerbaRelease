package com.emptydev.verba.editwords

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.emptydev.verba.*
import com.emptydev.verba.database.WordsDatabase
import com.emptydev.verba.databinding.EditWordsFragmentBinding
import com.emptydev.verba.delete.DeleteDialog
import com.emptydev.verba.mistakes.MistakesDialog
import com.emptydev.verba.training.TrainingType
import com.google.android.material.snackbar.Snackbar

class EditWordsFragment : Fragment() {


    private lateinit var viewModel: EditWordsViewModel

    private lateinit var binding: EditWordsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding= DataBindingUtil.inflate(inflater,R.layout.edit_words_fragment,container,false)
        val arguments = EditWordsFragmentArgs.fromBundle(requireArguments())
        val application = requireNotNull(this.activity).application
        Log.d("D_EditWordsFragment","onCreateView: ${arguments.wordsKey}");
        val dataSource= WordsDatabase.getInstance(application).wordsDatabaseDao
        val viewModelFactory=EditWordsViewModelFactory(arguments.wordsKey,dataSource)
        viewModel = ViewModelProvider(this,viewModelFactory).get(EditWordsViewModel::class.java)
        binding.viewModel=viewModel

        binding.lifecycleOwner=this
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_delete->DeleteDialog(requireContext(),{
                viewModel.deleteSet()
            }).show()
            R.id.action_home->goToHome()
        }
        return true
    }

    private fun goToHome() {
        findNavController().navigate(EditWordsFragmentDirections.actionEditWordsFragmentToWordsListFragment())
    }

    fun setupToolBar(){
        //binding.edWordsToolbar.title=viewModel.wordsSet.value!!.name
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.wordsSet.observe(viewLifecycleOwner, Observer {
            binding.textView.text=it?.name
            binding.textView.visibility=View.VISIBLE
            binding.setNameLayout.visibility=View.INVISIBLE
            binding.imageButton.setImageResource(R.drawable.ic_edit)

            val setStrings= arrayToPairStrings(stringToPairArray(it.words))
            binding.primaryText.setText(setStrings.first)
            binding.translatedText.setText(setStrings.second)
            binding.fabPlay.setImageResource(R.drawable.ic_play)
            setupToolBar()



        })
        viewModel.onEditSetName.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                binding.textView.visibility = View.GONE
                binding.setNameLayout.visibility = View.VISIBLE
                binding.edWordsName.setText(it!!)
                binding.imageButton.setImageResource(R.drawable.ic_check)
            }
        })
        viewModel.onSaveSetName.observe(viewLifecycleOwner, Observer {
            if (it==true){
                viewModel.saveSetName(binding.edWordsName.text.toString())
            }
        })
        viewModel.onSaveSet.observe(viewLifecycleOwner, Observer {
            Log.d("D_EditWordsFragment","onActivityCreated: save set");
            val primaryText=binding.primaryText.text.toString().trim()
            val translatedText=binding.translatedText.text.toString().trim()
            if (primaryText.isEmpty()){
                showExceptionInputEmpty(binding.fabPlay)
                return@Observer
            }
            if (translatedText.isEmpty()){
                showExceptionInputEmpty(binding.fabPlay)
                return@Observer
            }
            val   arrPrimary= stringToArray(primaryText)
            val arrTranslate= stringToArray(translatedText)
            if (arrPrimary.size!=arrTranslate.size){
                showExceptionInputNoneEqual(binding.fabPlay)
                return@Observer
            }

            val map = ArrayList<Pair<String,String>>()
            for(i in (0..arrPrimary.size-1) ){
                map.add(Pair(arrPrimary[i],arrTranslate[i]))
            }
            viewModel.saveSet(map)
        })
        viewModel.onPlaySet.observe(viewLifecycleOwner, Observer {
            if (it==true) {
                if (viewModel.wordsSet.value!!.numWords!=0) {
                    findNavController().navigate(EditWordsFragmentDirections.actionEditWordsFragmentToTrainingFragment(viewModel.wordsSet.value!!.wordId, processType()))
                    viewModel.onSetPlayed()
                }else{
                    showExceptionInputEmpty(binding.fabPlay)
                }
            }
        })
        viewModel.onShowLastMistakes.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                MistakesDialog(it).show(requireFragmentManager(),"Mistakes")
            }
        })
        viewModel.onSetDeleted.observe(viewLifecycleOwner, Observer {
            if (it==true){
                findNavController().navigate(EditWordsFragmentDirections.actionEditWordsFragmentToWordsListFragment())
            }
        })
        viewModel.textSaved.observe(viewLifecycleOwner, Observer {
            if (it==true){
                showTextSavedToast(binding.fabPlay)
            }
        })

        binding.translatedText.doOnTextChanged { text, start, before, count ->
            Log.d("D_EditWordsFragment","onActivityCreated: ${text!!.toString().trim()} ${before!!.toString().trim()}");
            if (text==null) return@doOnTextChanged
            if (before==null) return@doOnTextChanged

            if (containSavedTranslated(text)) return@doOnTextChanged
            if (!text.toString().trim().equals(before.toString().trim())) {
                binding.fabPlay.post {
                    binding.fabPlay.setImageResource(R.drawable.ic_save)
                }
                viewModel.onTextChanged()
                Log.d("D_EditWordsFragment","onActivityCreated: text changed");

            }
        }
        binding.primaryText.doOnTextChanged { text, start, before, count ->
            Log.d("D_EditWordsFragment","onActivityCreated: ${text!!.toString().trim()} ${before!!.toString().trim()}");
            if (text==null) return@doOnTextChanged
            if (before==null) return@doOnTextChanged

            if (containSavedLearning(text)) return@doOnTextChanged

            if (!text.toString().trim().equals(before.toString().trim())) {
                binding.fabPlay.post {
                    binding.fabPlay.setImageResource(R.drawable.ic_save)
                }
                viewModel.onTextChanged()
                Log.d("D_EditWordsFragment","onActivityCreated: text changed");
            }
        }
    }

    private fun containSavedTranslated(text: CharSequence): Boolean {
        val textString=text.toString().trim()
        val textArr= stringToArray(textString)
        val pairArr=stringToPairArray(viewModel.wordsSet.value!!.words)

        if (textArr.size!=pairArr.size) return false

        for(i in (0..pairArr.size-1)){
            if (!pairArr[i].second.equals(textArr[i])){
                return false
            }
        }
        return true
    }

    private fun containSavedLearning(text: CharSequence): Boolean {
        val textString=text.toString().trim()
        val textArr= stringToArray(textString)
        val pairArr=stringToPairArray(viewModel.wordsSet.value!!.words)

        if (textArr.size!=pairArr.size) return false

        for(i in (0..pairArr.size-1)){
            if (!pairArr[i].first.equals(textArr[i])){
                return false
            }
        }
        return true
    }


    private fun showTextSavedToast(v:View) {
        Snackbar.make(v,getString(R.string.text_saved),Snackbar.LENGTH_SHORT).show()
    }

    private fun showSetIsEmpty() {
        TODO("Not yet implemented")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_words, menu)
    }
    private fun showExceptionInputEmpty(view:View){
        Snackbar.make(view,getString(R.string.input_field_empty),Snackbar.LENGTH_LONG).show()

    }
    private fun showExceptionInputNoneEqual(view: View){
        Snackbar.make(view,getString(R.string.different_number_words),Snackbar.LENGTH_LONG).show()
    }
    private fun processType():TrainingType{
        val revers=binding.switchRevers.isChecked
        val last_mistakes=binding.swLastMistake.isChecked

        if (!revers && !last_mistakes) return TrainingType.BASIC

        if (revers && !last_mistakes) return TrainingType.REVERS

        if (!revers && last_mistakes) return TrainingType.LAST_MISTAKE

        if (revers && last_mistakes) return TrainingType.REVERS_LAST_MISTAKE

        return TrainingType.BASIC
    }



}
