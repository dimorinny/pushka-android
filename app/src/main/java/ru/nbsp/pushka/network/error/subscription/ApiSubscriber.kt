package ru.nbsp.pushka.network.error.subscription

import ru.nbsp.pushka.network.error.ApiErrorException
import ru.nbsp.pushka.network.error.subscription.annotation.ErrorHandler
import ru.nbsp.pushka.util.ErrorUtils
import rx.Subscriber
import java.lang.reflect.Method
import java.net.UnknownHostException
import java.util.*

/**
 * Created by Dimorinny on 24.05.16.
 */
class ApiSubscriber<T>(val delegate: ApiSubscriberDelegate<T>) : Subscriber<T>() {
    override fun onError(t: Throwable) {
        when (t) {
            is ApiErrorException -> {
                executeDelegateHandlers(t, t.code)
            }
            is UnknownHostException -> {
                executeDelegateHandlers(t, ErrorUtils.CONNECTION_ERROR_CODE)
            }
            else -> delegate.onError(t)
        }
    }

    override fun onCompleted() {
        delegate.onCompleted()
    }

    override fun onNext(data: T) {
        delegate.onNext(data)
    }

    private fun executeDelegateHandlers(t: Throwable, code: Int) {
        val methods = getDelegateMethodsWithCode(code)

        if (methods.isEmpty()) {
            delegate.onApiError(t, code)
        } else {
            for (method in methods) {
                method.invoke(delegate, t, code)
            }
        }
    }

    private fun getDelegateMethodsWithCode(code: Int): List<Method> {
        val methods = ArrayList<Method>()

        for (method in delegate.javaClass.methods) {
            for (annotation in method.annotations) {
                if (annotation is ErrorHandler && annotation.code == code) {
                    methods.add(method)
                }
            }
        }

        return methods
    }
}