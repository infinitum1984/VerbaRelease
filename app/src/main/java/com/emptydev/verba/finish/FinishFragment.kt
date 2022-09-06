package com.emptydev.verba.finish

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.emptydev.verba.R
import com.emptydev.verba.appContext
import com.emptydev.verba.database.WordsDatabase
import com.emptydev.verba.databinding.FinishFragmentBinding
import com.emptydev.verba.mistakes.MistakesDialog
import kotlin.math.roundToInt

class FinishFragment : Fragment() {
    private lateinit var binding:FinishFragmentBinding
    private lateinit var viewModel: FinishViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        
        binding=DataBindingUtil.inflate(inflater,R.layout.finish_fragment,container,false)
        val args=FinishFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory=FinishViewModelFactory(args.wordKey,WordsDatabase.getInstance(appContext(requireActivity())).wordsDatabaseDao,args.numCorrect,args.numException)
        viewModel=ViewModelProvider(this,viewModelFactory).get(FinishViewModel::class.java)
        binding.viewModel=viewModel
        viewModel.numMistakes.observe(viewLifecycleOwner, Observer {
            binding.tvMistakes.setText("${requireContext().getString(R.string.mistake)} ${it.first}/${it.second}")
        })
        viewModel.goodPercent.observe(viewLifecycleOwner, Observer {
            val precents=it!!.roundToInt()
            Log.d("D_FinishFragment","onCreateView: ${precents}");
            binding.progressBar.progress=precents
            binding.tvNumPrecents.text=precents.toString()+"%"

        })
        binding.fabHome.setOnClickListener {
            findNavController().navigate(FinishFragmentDirections.actionFinishFragmentToEditWordsFragment(viewModel.wordKey))
        }
        viewModel.onShowMistakes.observe(viewLifecycleOwner, Observer {
            if (it==true){
                MistakesDialog(viewModel.mistakes).show(requireFragmentManager(),"Mistakes")
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.finish_menu,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home-> goToHome()
        }
        return true
    }

    private fun goToHome() {
        findNavController().navigate(FinishFragmentDirections.actionFinishFragmentToWordsListFragment())
    }


}
