package ru.nbsp.pushka.presentation.subscription.params.control

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.alert.feed.adapter.AlertsAdapter
import ru.nbsp.pushka.presentation.core.adapter.OnItemClickListener
import ru.nbsp.pushka.presentation.core.state.State
import java.util.*

/**
 * Created by egor on 20.03.16.
 */
class SimpleListControl
    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs), Control {

//    private val list: ArrayList<DropdownControl.Option>

    internal class Option(var title: String, var value: String) {
        override fun toString(): String {
            return title
        }
    }

    private val titleView: TextView
    private val errorIndicator: TextView

    init {
        orientation = LinearLayout.HORIZONTAL
        setGravity(Gravity.CENTER_VERTICAL)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.control_simple_list, this, true)

        titleView = findViewById(R.id.titleView) as TextView
        errorIndicator = findViewById(R.id.errorIndicator) as TextView

    }

    override fun getValue(): String? {
        throw UnsupportedOperationException()
    }

    override fun setOnChangeCallback(callback: Control.Callback) {
        throw UnsupportedOperationException()
    }

    override fun setError() {
        throw UnsupportedOperationException()
    }

    override fun setNoError() {
        throw UnsupportedOperationException()
    }
}