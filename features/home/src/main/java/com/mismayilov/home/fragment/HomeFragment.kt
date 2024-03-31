package com.mismayilov.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mismayilov.core.listener.ConnectivityViewModel
import com.mismayilov.home.R
import com.mismayilov.home.adapter.InvestRecyclerAdapter
import com.mismayilov.home.databinding.FragmentHomeBinding
import com.mismayilov.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(){
    private lateinit var connectivityViewModel: ConnectivityViewModel
    private val adapter = InvestRecyclerAdapter()
    private val viewModel by viewModels<HomeViewModel>()
    private var binding: FragmentHomeBinding? = null
    private var firstInternetConnection: Boolean? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context), container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initConnectionListener()
        initListeners()
        initRecycler()
    }

    private fun initConnectionListener() {
        connectivityViewModel = ViewModelProvider(requireActivity())[ConnectivityViewModel::class.java]
        connectivityViewModel.isConnected.observe(viewLifecycleOwner) { isConnected ->
            if (!isConnected && firstInternetConnection == true) viewModel.saveData()
            else if (firstInternetConnection == null && isConnected)viewModel.fetchData()
            if (firstInternetConnection == null) firstInternetConnection = isConnected
        }
    }

    private fun initRecycler() {
        binding?.apply {
            investRecyclerView.setHasFixedSize(true)
            investRecyclerView.layoutManager = GridLayoutManager(context, 1)
            investRecyclerView.adapter = adapter
        }
    }

    private fun initListeners() {
        viewModel.onConnect.observe(viewLifecycleOwner) { isConnected ->
            binding?.apply {
                if (isConnected) {
                    homeAnimationView.setAnimation(R.raw.green_indicator)
                    homeIndicatorTxt.text = getString(R.string.connect)
                    homeAnimationView.playAnimation()
                    viewModel.saveData()
                } else {
                    homeIndicatorTxt.text = getString(R.string.disconnected)
                    homeAnimationView.setAnimation(R.raw.red_indicator)
                    homeAnimationView.playAnimation()
                }
            }
        }
        viewModel.dataLiveData.observe(viewLifecycleOwner) { data ->
            if (data.isNotEmpty() && binding!!.progressBar.visibility == View.VISIBLE) binding!!.progressBar.visibility = View.GONE
            adapter.submitList(data)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}