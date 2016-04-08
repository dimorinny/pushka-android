package ru.nbsp.pushka.presentation.subscription.params

import ru.nbsp.pushka.presentation.core.base.BasePresenter
import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import java.util.*
import javax.inject.Inject

/**
 * Created by egor on 17.03.16.
 */
class ParamsPresenter
    @Inject constructor(): BasePresenter {

    override var view: ParamsView? = null

    var params: List<PresentationParam> = ArrayList()
        set(value) {
            field = value
            showParams()
        }

    private fun showParams() {
        for (param in params) {
            view?.addParam(param)
        }
    }

    fun onParamChanged(param: PresentationParam, newValue: String?) {
        if (param.required && newValue != null) {
            view!!.setNoError(param)
        }
    }

    fun validate(): Boolean {
        var errorFlag = false
        for (param in params) {
            if (param.required) {
                val value = view!!.getValue(param)
                if (value == null) {
                    view!!.setError(param)
                    errorFlag = true
                }
            }
        }
        return !errorFlag
    }

    fun getParamsMap(): Map<String, String?> {
        var map = HashMap<String, String?>()
        for (param in params) {
            map.put(param.name, view!!.getValue(param))
        }
        return map
    }
}
