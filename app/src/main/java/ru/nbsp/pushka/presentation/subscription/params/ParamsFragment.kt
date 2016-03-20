package ru.nbsp.pushka.presentation.subscription.params

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import ru.nbsp.pushka.BaseApplication
import ru.nbsp.pushka.R

import java.util.ArrayList

import javax.inject.Inject

import ru.nbsp.pushka.presentation.PresentedFragment
import ru.nbsp.pushka.presentation.core.model.source.PresentationControl
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.subscription.params.control.Control
import ru.nbsp.pushka.presentation.subscription.params.inflaters.ControlInflater

/**
 * Created by egor on 16.03.16.
 */
class ParamsFragment : PresentedFragment<ParamsPresenter>(), ParamsView {

    @Inject
    lateinit var presenter: ParamsPresenter
    lateinit var layout: DictLayout

    var paramsToSet: List<PresentationParam>? = null
    var presenterInited = false

    fun validate(): Boolean {
        return presenter.validate()
    }

    fun getAsMap(): Map<String, String?> {
        return presenter.getAsMap()
    }

    fun setParams(params: List<PresentationParam>) {
        if (!presenterInited) {
            paramsToSet = params
        } else {
            presenter.params = params
        }
    }

    override fun setNoError(param: PresentationParam) {
        (layout.get(param.name) as Control).setNoError()
    }

    override fun setError(param: PresentationParam) {
        (layout.get(param.name) as Control).setError()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_params, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout = view!!.findViewById(R.id.dict_layout) as DictLayout

        BaseApplication.graph.inject(this)
        initPresenter(presenter)
    }

    override fun initPresenter(presenter: ParamsPresenter) {
        presenter.view = this
        if (paramsToSet != null) {
            presenter.params = paramsToSet!!
        }
        super.initPresenter(presenter)
        presenterInited = true
    }

    override fun addParam(param: PresentationParam) {
        val control = ControlInflater.inflate(param.control, context)
        layout.addPair(param.name, control as View)
        val cb = object : Control.Callback {
            override fun onChange(newValue: String?) {
                presenter.onParamChanged(param, newValue)
            }
        }
        control.setOnChangeCallback(cb)
    }


    companion object {
        val ARG_SOURCE = "arg_source"
    }

    override fun getValue(param: PresentationParam): String? {
        return (layout.get(param.name) as Control).getValue()
    }
}
