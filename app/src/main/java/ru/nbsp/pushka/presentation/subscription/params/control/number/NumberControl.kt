package ru.nbsp.pushka.presentation.subscription.params.control.number

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.core.model.subscription.control.NumberAttributes
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import ru.nbsp.pushka.util.bindView

/**
 * Created by Dimorinny on 11.04.16.
 */
class NumberControl(context: Context, val attributes: NumberAttributes, attrs: AttributeSet? = null)
        : LinearLayout(context, attrs), Control {

    val title: TextView by bindView(R.id.number_input_title)
    val error: TextView by bindView(R.id.number_input_error)
    val number: DiscreteSeekBar by bindView(R.id.number_input)

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.control_number, this, true)
        initViews()
    }

    private fun initViews() {
        number.min = attributes.minValue.toFloat().toInt()
        number.max = attributes.maxValue.toFloat().toInt()
        number.progress = attributes.default.toFloat().toInt()
    }

    override fun setNoError() {
        error.visibility = GONE
    }

    override fun setError() {
        error.visibility = VISIBLE
        error.text = "ERROR"
    }

    fun setTitle(value: String) {
        title.text = value
    }

    override fun getValue(): String? = number.progress.toString()

    override fun setValue(value: String?) {
//        number.progress = value.toFloat().toInt()
    }

    override fun setOnChangeListener(onChangeListener: Control.OnChangeListener) {
        number.setOnProgressChangeListener(object : DiscreteSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: DiscreteSeekBar?, value: Int, fromUser: Boolean) {
                onChangeListener.onChange(value.toString())
            }

            override fun onStartTrackingTouch(seekBar: DiscreteSeekBar?) {}

            override fun onStopTrackingTouch(seekBar: DiscreteSeekBar?) {}
        })
    }
}