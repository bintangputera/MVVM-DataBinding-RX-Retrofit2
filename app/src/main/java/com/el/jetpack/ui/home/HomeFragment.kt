package com.el.jetpack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.el.jetpack.R
import com.el.jetpack.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _bindingHomeFragment : FragmentHomeBinding? = null
    private val binding get() = _bindingHomeFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingHomeFragment = FragmentHomeBinding.inflate(inflater, container, false)
        return _bindingHomeFragment?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnTodo?.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_todoFragment)
        }
        binding?.btnPost?.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_postsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingHomeFragment = null
    }

}