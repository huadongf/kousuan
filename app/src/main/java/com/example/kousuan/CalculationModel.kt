package com.example.kousuan
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import java.util.*
class CalculationModel(application: Application, handle: SavedStateHandle) : AndroidViewModel(application) {
    private val handle: SavedStateHandle
    companion object {
        private const val KEY_CURRENTTIME = "currenttime"   //当前剩余时间
        private const val KEY_DIFF = "nandu"      //难度
        private const val KEY_HIGH_SCORE = "key_high_score" //最高记录
        private const val KEY_LEFT_NUMBER = "key_left_number" //第一个运算数
        private const val KEY_RIGHT_NUMBER = "key_right_number" //第二个运算数
        private const val KEY_OPERATOR = "key_operator" //运算操作符
        private const val KEY_ANSWER = "key_answer" //运算答案
        private const val KEY_SAVE_SP_DATA_NAME = "save_sp_data_name" //保存的数据
        private const val KEY_CURRENT_SCORE = "key_current_score" //当前得分
    }
    init {
        if (!handle.contains(KEY_HIGH_SCORE)) {
            val preferences = getApplication<Application>().getSharedPreferences(KEY_SAVE_SP_DATA_NAME, Context.MODE_PRIVATE)
            handle.set(KEY_HIGH_SCORE, preferences.getInt(KEY_HIGH_SCORE, 0))
            handle.set(KEY_LEFT_NUMBER, 0)
            handle.set(KEY_RIGHT_NUMBER, 0)
            handle.set(KEY_OPERATOR, "+")
            handle.set(KEY_ANSWER, 0)
            handle.set(KEY_CURRENT_SCORE, 0)
        }
        this.handle = handle
    }
    val currenttime: MutableLiveData<Int>
        get() = handle.getLiveData(KEY_CURRENTTIME)
    val diff: MutableLiveData<Long>
        get() = handle.getLiveData(KEY_DIFF)
    val leftNumber: MutableLiveData<Int>
        get() = handle.getLiveData(KEY_LEFT_NUMBER)
    val rightNumber: MutableLiveData<Int>
        get() = handle.getLiveData(KEY_RIGHT_NUMBER)
    val operation: MutableLiveData<String>
        get() = handle.getLiveData(KEY_OPERATOR)
    val highSocre: MutableLiveData<Int>
        get() = handle.getLiveData(KEY_HIGH_SCORE)
    val currentScore: MutableLiveData<Int>
        get() = handle.getLiveData(KEY_CURRENT_SCORE)
    val answer: MutableLiveData<Int>
        get() = handle.getLiveData(KEY_ANSWER)
    var winflag = false
    fun save() {
        val preferences = getApplication<Application>().getSharedPreferences(KEY_SAVE_SP_DATA_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt(KEY_HIGH_SCORE, highSocre.value!!)
        editor.apply()
    }
    fun answerCorrect() {
        currentScore.value = currentScore.value!! + 1
        if (currentScore.value!! > highSocre.value!!) {
            highSocre.value = currentScore.value
            winflag = true
        }
        generator()
    }
    fun generator() {
        val level = 100
        val random = Random()
        val x: Int
        val y: Int
        x = random.nextInt(level) + 1
        y = random.nextInt(level) + 1
        if (x % 2 == 0) {
            operation.value = "+"
            if (x > y) {
                answer.value = x
                leftNumber.value = y
                rightNumber.setValue(x - y)
            } else {
                answer.value = y
                leftNumber.value = x
                rightNumber.setValue(y - x)
            }
        } else {
            operation.value = "-"
            if (x > y) {
                answer.value = x - y
                leftNumber.value = x
                rightNumber.setValue(y)
            } else {
                answer.value = y - x
                leftNumber.value = y
                rightNumber.setValue(x)
            }
        }
    }
}