package ru.nbsp.pushka.repository.subscription

import ru.nbsp.pushka.presentation.core.model.subscription.PresentationSubscription
import rx.Observable

/**
 * Created by Dimorinny on 26.02.16.
 */
class FakeSubscriptionsRepository : SubscriptionsRepository {

    companion object {
        private const val SUBSCRIPTION_TITLE1 = "Погода в москве"
        private const val SUBSCRIPTION_SUBTITLE1 = "Погода в москве каждый вечер"

        private const val SUBSCRIPTION_TITLE2 = "Курсы валют"
        private const val SUBSCRIPTION_SUBTITLE2 = "В одном долларе меньше 100 рублей"

        private const val SUBSCRIPTION_TITLE3 = "Фильмы"
        private const val SUBSCRIPTION_SUBTITLE3 = "Выход фильма: \"Великолепная пятерка\""
    }

    override fun getSubscriptions(): Observable<List<PresentationSubscription>> {
        return Observable.just(listOf(
                PresentationSubscription(SUBSCRIPTION_TITLE1, SUBSCRIPTION_SUBTITLE1),
                PresentationSubscription(SUBSCRIPTION_TITLE2, SUBSCRIPTION_SUBTITLE2),
                PresentationSubscription(SUBSCRIPTION_TITLE3, SUBSCRIPTION_SUBTITLE3),
                PresentationSubscription(SUBSCRIPTION_TITLE1, SUBSCRIPTION_SUBTITLE1),
                PresentationSubscription(SUBSCRIPTION_TITLE2, SUBSCRIPTION_SUBTITLE2),
                PresentationSubscription(SUBSCRIPTION_TITLE3, SUBSCRIPTION_SUBTITLE3),
                PresentationSubscription(SUBSCRIPTION_TITLE1, SUBSCRIPTION_SUBTITLE1),
                PresentationSubscription(SUBSCRIPTION_TITLE2, SUBSCRIPTION_SUBTITLE2),
                PresentationSubscription(SUBSCRIPTION_TITLE3, SUBSCRIPTION_SUBTITLE3)
        ))
    }
}