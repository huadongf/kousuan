package com.example.kousuan
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateVMFactory
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.kousuan.databinding.FragmentQuestionBinding
class QuestionFragment : Fragment() {
    private lateinit var timer: CountDownTimer
    lateinit var model: CalculationModel
    override fun onPause() {
        super.onPause()
        timer.cancel()
        model.currentScore.value = 0
    }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        model = ViewModelProviders.of(requireActivity(), SavedStateVMFactory(requireActivity())).get(CalculationModel::class.java)
        model.generator()
        val binding: FragmentQuestionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false)
        binding.data = model
        binding.lifecycleOwner = requireActivity()
        val builder = StringBuilder()
        val mMainNavFragment: Fragment = this
        timer = object : CountDownTimer(model.diff.value!!, 200) {
            override fun onTick(millisUntilFinished: Long) {
                model.currenttime.value = (millisUntilFinished / 1000).toInt() + 1
            }
            override fun onFinish() {
                if (model.winflag) NavHostFragment.findNavController(mMainNavFragment).navigate(R.id.action_questionFragment_to_winFragment) else NavHostFragment.findNavController(mMainNavFragment).navigate(R.id.action_questionFragment_to_loseFragment)
            }
        }
        timer.start()
        val listener = View.OnClickListener { v ->
            when (v.id) {
                R.id.btn0 -> builder.append("0")
                R.id.btn1 -> builder.append("1")
                R.id.btn2 -> builder.append("2")
                R.id.btn3 -> builder.append("3")
                R.id.btn4 -> builder.append("4")
                R.id.btn5 -> builder.append("5")
                R.id.btn6 -> builder.append("6")
                R.id.btn7 -> builder.append("7")
                R.id.btn8 -> builder.append("8")
                R.id.btn9 -> builder.append("9")
                R.id.btnClear -> builder.setLength(0)
            }
            if (builder.isEmpty()) {
                binding.textView9.text = getString(R.string.input_indicator)
            } else {
                binding.textView9.text = getString(R.string.input_indicator) + builder.toString()
            }
        }
        binding.btn0.setOnClickListener(listener)
        binding.btn1.setOnClickListener(listener)
        binding.btn2.setOnClickListener(listener)
        binding.btn3.setOnClickListener(listener)
        binding.btn4.setOnClickListener(listener)
        binding.btn5.setOnClickListener(listener)
        binding.btn6.setOnClickListener(listener)
        binding.btn7.setOnClickListener(listener)
        binding.btn8.setOnClickListener(listener)
        binding.btn9.setOnClickListener(listener)
        binding.btnClear.setOnClickListener(listener)
        binding.btnSubmit.setOnClickListener { v ->
            if (builder.isEmpty())
                builder.append("-" + Int.MAX_VALUE)
            if (builder.length <= 8 && Integer.valueOf(builder.toString()) == model.answer.value) {
                model.answerCorrect()
                builder.setLength(0)
                binding.textView9.text = getString(R.string.answer_correct_message)
                timer.cancel()
                timer.start()
            } else {
                timer.cancel()
                val controller = Navigation.findNavController(v)
                if (model.winflag) {
                    controller.navigate(R.id.action_questionFragment_to_winFragment)
                    model.winflag = false
                    model.save()
                }
                else
                    controller.navigate(R.id.action_questionFragment_to_loseFragment)
            }
        }
        return binding.root
    }
}
