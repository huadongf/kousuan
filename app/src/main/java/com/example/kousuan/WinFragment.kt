package com.example.kousuan
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateVMFactory
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.kousuan.databinding.FragmentWinBinding
class WinFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val model = ViewModelProviders.of(requireActivity(), SavedStateVMFactory(requireActivity())).get(CalculationModel::class.java)
        val binding: FragmentWinBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_win, container, false)
        binding.data = model
        binding.lifecycleOwner = requireActivity()
        binding.button12.setOnClickListener { v ->
            val controller = Navigation.findNavController(v)
            controller.navigate(R.id.action_winFragment_to_welcomeFragment)
        }
        return binding.root
    }
}