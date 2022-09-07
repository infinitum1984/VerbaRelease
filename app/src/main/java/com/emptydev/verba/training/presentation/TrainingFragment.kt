package com.emptydev.verba.training.presentation

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.emptydev.verba.R
import com.emptydev.verba.databinding.TrainingFragmentBinding
import com.emptydev.verba.vibratePhone
import org.koin.core.parameter.parametersOf
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class TrainingFragment : Fragment() {


    val viewModel: TrainingViewModel by viewModel{
        val args= TrainingFragmentArgs.fromBundle(requireArguments())
        parametersOf(args.wordsKey, args.trainingType)
    }
    private lateinit var binding:TrainingFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val args= TrainingFragmentArgs.fromBundle(requireArguments())

        binding=DataBindingUtil.inflate(inflater, R.layout.training_fragment, container,false)
        binding.setLifecycleOwner(this)

        binding.viewModel=viewModel
        viewModel.curPairWords.observe(viewLifecycleOwner, Observer {
            binding.tvResult.visibility=View.INVISIBLE
            Log.d("D_TrainingFragment","onCreateView: ${it.first} ${it.second}");
            binding.tvWord.setText(it.second)
            binding.edWord.setText("")
        })
        viewModel.onShowMistake.observe(viewLifecycleOwner, Observer {
            Log.d("D_TrainingFragment","onCreateView: ${it}");
            if (it!=null) {
                if (it == true) {
                    showMistake()

                } else {
                    showRight()
                }
            }
        })
        viewModel.nameWordsList.observe(viewLifecycleOwner, Observer {
            setupActionBar(it!!)
        })
        viewModel.onChangeBtnText.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                binding.button.text=requireContext().getString(it)
            }
        })
        viewModel.onVerifyWords.observe(viewLifecycleOwner, Observer {
            if (it==true){
                viewModel.verifyWords(binding.edWord.text.toString())
            }
        })
        viewModel.mistakes.observe(viewLifecycleOwner, Observer {
            if (it!=null) showNumMistakes(it.size)
        })
        viewModel.curWordPairNum.observe(viewLifecycleOwner, Observer {
            if (it!=null) showWordNum(it)
        })
        viewModel.toFinish.observe(viewLifecycleOwner, Observer {
            if (it==true) findNavController().navigate(
                TrainingFragmentDirections.actionTrainingFragmentToFinishFragment(
                    args.wordsKey,
                    viewModel.Words.size,
                    viewModel.Mistakes.size
                )
            )
        })
        return binding.root
    }

    private fun showRight() {
       binding.tvResult.setText(getString(R.string.right))

        binding.tvResult.visibility=View.VISIBLE

    }
    private fun showWordNum(nums:Pair<Int,Int>){
        binding.tvNumWords.text="${requireContext().getString(R.string.word)}: ${nums.first}/${nums.second}"
    }
    private fun showMistake() {
        this.vibratePhone()

        binding.tvResult.setText(requireContext().getString(R.string.mistake))

        binding.tvResult.visibility=View.VISIBLE

    }
    private fun showNumMistakes(num: Int){
        binding.tvNumMistackes.text="${requireContext().getString(R.string.mistake)}: ${num}"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("D_TrainingFragment","onActivityCreated: ");
        // TODO: Use the ViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.training_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home_item->goToHome()
        }
        return true
    }
    private fun setupActionBar(setName:String){
//        val myActionBar=requireActivity().actionBar!!
  //      myActionBar.title=setName
    }
    private fun goToHome() {
        findNavController().navigate(TrainingFragmentDirections.actionTrainingFragmentToWordsListFragment())
    }


}
