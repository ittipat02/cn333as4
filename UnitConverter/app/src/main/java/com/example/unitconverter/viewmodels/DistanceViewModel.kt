package com.example.unitconverter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitconverter.R
import java.lang.NumberFormatException

class DistanceViewModel: ViewModel() {
    private  val _scale: MutableLiveData<Int> = MutableLiveData(R.string.meter)

    val scale: LiveData<Int>
        get() = _scale

    fun setScale(value: Int) {
        _scale.value = value
    }

    private val _distance: MutableLiveData<String> = MutableLiveData("")

    val distance: LiveData<String>
        get() = _distance

    fun getDistanceAsFloat(): Float = (_distance.value ?: "")?.let {
        return try {
            it.toFloat()
        } catch (e: NumberFormatException) {
            Float.NaN
        }

    }

    fun setDistance(value: String){
        _distance.value = value
    }

    fun convert() = getDistanceAsFloat().let {
        if (!it.isNaN())
            if (_scale.value == R.string.meter)
                (it * 0.00062137F)
            else
                (it * 1609.34F)
        else
            Float.NaN
    }
}