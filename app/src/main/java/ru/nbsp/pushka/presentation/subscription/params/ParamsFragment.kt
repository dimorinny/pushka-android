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
import javax.inject.Inject

/**
 * Created by egor on 16.03.16.
 */
class ParamsFragment : PresentedFragment<ParamsPresenter>(), ParamsView {

    @Inject
    lateinit var presenter: ParamsPresenter

    @Inject
    lateinit var controlBuilder: ControlBuilder

    var paramsToSet: List<PresentationParam>? = null
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
    }

    override fun initPresenter(presenter: ParamsPresenter) {
        presenter.view = this
        if (paramsToSet != null) {
            presenter.params = paramsToSet!!
        }
        super.initPresenter(presenter)
        presenterInited = true
    }

    fun validate(): Boolean {
        return presenter.validate()
    }

    fun getParamsMap(): Map<String, String?> {
        return presenter.getParamsMap()
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

    override fun addParam(param: PresentationParam) {
        val control = controlBuilder.build(param.control, context)
        layout.addPair(param.name, control as View)
        control.setOnChangeListener(object : Control.OnChangeListener {
            override fun onChange(newValue: String?) {
                presenter.onParamChanged(param, newValue)
            }
        })
    }

    override fun getValue(param: PresentationParam): String? {
        return (layout.get(param.name) as Control).getValue()
    }

    override fun showParams() {
        container.visibility = View.VISIBLE
    }

    override fun hideParams() {
        container.visibility = View.GONE
    }
}
