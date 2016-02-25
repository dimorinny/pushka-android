package ru.nbsp.pushka.repository.alert

import ru.nbsp.pushka.api.model.alert.Alert
import rx.Observable

/**
 * Created by Dimorinny on 25.02.16.
 */
class FakeAlertsRepository : AlertsRepository {

    companion object {
        private const val FAKE_IMAGE = "https://pp.vk.me/c624429/v624429144/47ce8/_oLolQK_-Uo.jpg"
        private const val FAKE_TEXT = "When talking about the Android Support Library, it is important to realize this isn’t one monolithic library, but a whole collection of..."
    }

    override fun getAlerts(): Observable<List<Alert>> {
        return Observable.just(listOf(
                Alert("Title1", FAKE_TEXT, null, FAKE_IMAGE, "Газета.ру", "qwe"),
                Alert("Title2", FAKE_TEXT, FAKE_IMAGE, FAKE_IMAGE, "Газета.ру", "qwe"),
                Alert("Title3", FAKE_TEXT, null, FAKE_IMAGE, "Газета.ру", "qwe"),
                Alert("Title4", FAKE_TEXT, FAKE_IMAGE, FAKE_IMAGE, "Газета.ру", "qwe")
        ))
    }
}