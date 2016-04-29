package ru.nbsp.pushka.presentation.subscription.params


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R
import ru.nbsp.pushka.presentation.PresentedFragment
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.subscription.params.builders.ControlBuilder
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import ru.nbsp.pushka.util.bindView
import java.io.Serializable
import java.util.*
import javax.inject.Inject

/**
 * Created by egor on 16.03.16.
 */
class ParamsFragment : PresentedFragment<ParamsPresenter>(), ParamsView {

    companion object {
        const val STATE_VALUES = "state_values"
    }

    @Inject
    lateinit var presenter: ParamsPresenter

    @Inject
    lateinit var controlBuilder: ControlBuilder

    var paramsToSet: List<PresentationParam>? = null
    var valuesToSet: Map<String, String?>? = null

    var presenterInited = false

    val container: ViewGroup by bindView(R.id.params_container)
    val layout: DictLayout by bindView(R.id.dict_layout)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_params, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BaseApplication.graph.inject(this)

        initPresenter(presenter)
        initValues(savedInstanceState)
    }

    @Suppress("UNCHECKED_CAST")
    private fun initValues(savedInstanceState: Bundle?) {
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_VALUES)) {
            setValues(savedInstanceState.getSerializable(STATE_VALUES) as Map<String, String?>)
        }
    }

    override fun initPresenter(presenter: ParamsPresenter) {
        presenter.view = this
        if (paramsToSet != null) {
            presenter.params = paramsToSet!!
        }

        if (valuesToSet != null) {
            presenter.setValues(valuesToSet!!)
        }
        super.initPresenter(presenter)
        presenterInited = true
    }

    fun validate(): Boolean {
        return presenter.validate()
    }

    fun getValues(): HashMap<String, String?> {
        return presenter.getValues()
    }

    fun setValues(values: Map<String, String?>) {
        if (!presenterInited) {
            valuesToSet = values
        } else {
            presenter.setValues(values)
        }
    }

    fun setParams(params: List<PresentationParam>) {
        if (!presenterInited) {
            paramsToSet = params
        } else {
            presenter.params = params
        }
    }

    override fun setNoError(name: String) {
        (layout.get(name) as Control).setNoError()
    }

    override fun setError(name: String) {
        (layout.get(name) as Control).setError()
    }

    override fun addParam(param: PresentationParam) {
        val control = controlBuilder.build(param.control, context)
        layout.addPair(param.name, control as View)
        control.setOnChangeListener(object : Control.OnChangeListener {
            override fun onChange(newValue: String?) {
                presenter.onParamChanged(param, newValue)
            }
        })
    }

    override fun getValue(name: String): String? {
        return (layout.get(name) as Control).getValue()
    }

    override fun setValue(name: String, value: String?) {
        (layout.get(name) as Control).setValue(value)
    }

    override fun showParams() {
        container.visibility = View.VISIBLE
    }

    override fun hideParams() {
        container.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(STATE_VALUES, getValues() as Serializable)
        super.onSaveInstanceState(outState)
    }
}
