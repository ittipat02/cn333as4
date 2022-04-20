package com.example.unitconverter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitconverter.R
import java.lang.NumberFormatException

class WeightViewModel: ViewModel() {
    private  val _scale: MutableLiveData<Int> = MutableLiveData(R.string.pound)

    val scale: LiveData<Int>
        get() = _scale

    fun setScale(value: Int) {
        _scale.value = value
    }

    private val _weight: MutableLiveData<String> = MutableLiveData("")

    val weight: LiveData<String>
        get() = _weight

    fun getWeightAsFloat(): Float = (_weight.value ?: "")?.let {
        return try {
            it.toFloat()
        } catch (e: NumberFormatException) {
            Float.NaN
        }

    }

    fun setWeight(value: String){
        _weight.value = value
    }

    fun convert() = getWeightAsFloat().let {
        if (!it.isNaN())
            if (_scale.value == R.string.pound)
                (it * 0.453592F)
            else
                (it * 2.20462F)
        else
            Float.NaN
    }
}