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
import com.example.kousuan.databinding.FragmentLoseBinding
class LoseFragment : Fragment() {
    private lateinit var model: CalculationModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        model = ViewModelProviders.of(requireActivity(), SavedStateVMFactory(requireActivity())).get(CalculationModel::class.java)
        val binding: FragmentLoseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_lose, container, false)
        binding.data = model
        binding.lifecycleOwner = requireActivity()
        binding.button11.setOnClickListener { v ->
            model.currentScore.value = 0
            val controller = Navigation.findNavController(v)
            controller.navigate(R.id.action_loseFragment_to_welcomeFragment)
        }
        return binding.root
    }
}