package com.example.kousuan
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateVMFactory
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.kousuan.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    lateinit var model: CalculationModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        model = ViewModelProviders.of(requireActivity(), SavedStateVMFactory(requireActivity())).get(CalculationModel::class.java)
        val binding: FragmentWelcomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
        binding.data = model
        binding.lifecycleOwner = requireActivity()
        binding.button.setOnClickListener { v ->
            val controller = Navigation.findNavController(v)
            controller.navigate(R.id.action_welcomeFragment_to_questionFragment)
        }
        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, pos: Int, id: Long) {
                val tv = view as TextView
                tv.textSize = 20f //设置大小
                tv.gravity = Gravity.CENTER_HORIZONTAL //设置居中
                val a = resources.getStringArray(R.array.nandu)
                if (a[pos] == "简单") model.diff.setValue(9000) else if (a[pos] == "中等") model.diff.setValue(6000) else model.diff.setValue(3000)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        return binding.root
    }
}