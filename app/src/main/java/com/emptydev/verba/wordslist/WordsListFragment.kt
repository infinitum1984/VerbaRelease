package com.emptydev.verba.wordslist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.emptydev.verba.R
import com.emptydev.verba.database.WordsDatabase
import com.emptydev.verba.databinding.WordsListFragmentBinding
import com.emptydev.verba.delete.DeleteDialog
import com.emptydev.verba.training.TrainingType
import com.google.android.material.snackbar.Snackbar


class WordsListFragment : Fragment() {

    private lateinit var viewModel: WordsListViewModel
    private lateinit var binding: WordsListFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding=DataBindingUtil.inflate(inflater,R.layout.words_list_fragment,container,false)
        val dataSource=WordsDatabase.getInstance(requireContext()).wordsDatabaseDao
        val viewModelFactory=WordsListViewModelFactory(dataSource,requireContext())
        val viewModel= ViewModelProvider(this, viewModelFactory).get(WordsListViewModel::class.java)
        binding.viewModel=viewModel
        binding.lifecycleOwner=this
        val adapter=WordsListAdapter({
          viewModel.onPlaySet(it)
        },{wordId,action->
            when(action){
                WordsListHolder.Action.DELETE->deleteSet(wordId)
                WordsListHolder.Action.PLAY->playSet(wordId)
            }
        })
        binding.wordsList.adapter=adapter
        viewModel.words.observe(viewLifecycleOwner, Observer {

            adapter.setData(it)

        })
        viewModel.navigateToEditWords.observe(viewLifecycleOwner, Observer {
            if (it!=null) {
                this.findNavController()
                        .navigate(WordsListFragmentDirections.actionWordsListFragmentToEditWordsFragment(it))
                viewModel.doneNavigation()
            }
        })
        viewModel.onFastPlaySet.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                findNavController().navigate(WordsListFragmentDirections.actionWordsListFragmentToTrainingFragment(it,TrainingType.BASIC))

            }
        })
        viewModel.setIsEmpty.observe(viewLifecycleOwner, Observer {
            if (it==true){
                showSetIsEmptyException();
            }
        })
        val layoutManager=LinearLayoutManager(requireContext())

        val divider=ContextCompat.getDrawable(requireContext(),R.drawable.devider)
        val dividerItemDecoration = DividerItemDecoration(requireContext(),
                layoutManager.getOrientation())
        dividerItemDecoration.setDrawable(divider!!)
        binding.wordsList.addItemDecoration(dividerItemDecoration)
        binding.wordsList.layoutManager=layoutManager
        return binding.root
    }

    private fun showSetIsEmptyException() {
        Snackbar.make(binding.floatingActionButton,getString(R.string.set_is_empty),Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.word_list_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exit_action->exitApp()
        }
        return true
    }
    private fun exitApp() {
        requireActivity().finish()
    }
    private fun playSet(wordId:Long){
        viewModel.fastPlaySet(wordId)
    }
    private fun deleteSet(setId:Long){
        DeleteDialog(requireContext(),{
            if (it==true){
                viewModel.deleteSet(setId)
            }
        }).show()

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WordsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
