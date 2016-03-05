package ru.nbsp.pushka.repository.source

import ru.nbsp.pushka.data.model.source.Param
import ru.nbsp.pushka.data.model.source.Source
import rx.Observable
import java.util.*

/**
 * Created by Dimorinny on 26.02.16.
 */
class FakeSourcesRepository : SourcesRepository {

    companion object {
        private const val FAKE_SOURCE_TITLE = "Фильмы"
        private const val FAKE_SOURCE_SUBTITLE = "Подписка на новинки киноиндустрии"

        private const val FAKE_SOURCE_TITLE2 = "Сериалы"
        private const val FAKE_SOURCE_SUBTITLE2 = "Не пропусти новую серию любимого сериала"

        private const val FAKE_SOURCE_TITLE3 = "Новости"
        private const val FAKE_SOURCE_SUBTITLE3 = "Будь всегда в курсе интерисующих тебя тематик"

        private const val FAKE_ID = "some_id"
        private const val FAKE_HANDLER_ID = "some_handler_id"
    }

    override fun getSources(): Observable<List<Source>> {
        return Observable.just(listOf(
                Source(FAKE_ID, ArrayList<Param>(), FAKE_SOURCE_TITLE, FAKE_SOURCE_SUBTITLE, FAKE_HANDLER_ID),
                Source(FAKE_ID, ArrayList<Param>(), FAKE_SOURCE_TITLE2, FAKE_SOURCE_SUBTITLE2, FAKE_HANDLER_ID),
                Source(FAKE_ID, ArrayList<Param>(), FAKE_SOURCE_TITLE3, FAKE_SOURCE_SUBTITLE3, FAKE_HANDLER_ID),
                Source(FAKE_ID, ArrayList<Param>(), FAKE_SOURCE_TITLE, FAKE_SOURCE_SUBTITLE, FAKE_HANDLER_ID),
                Source(FAKE_ID, ArrayList<Param>(), FAKE_SOURCE_TITLE2, FAKE_SOURCE_SUBTITLE2, FAKE_HANDLER_ID),
                Source(FAKE_ID, ArrayList<Param>(), FAKE_SOURCE_TITLE3, FAKE_SOURCE_SUBTITLE3, FAKE_HANDLER_ID),
                Source(FAKE_ID, ArrayList<Param>(), FAKE_SOURCE_TITLE, FAKE_SOURCE_SUBTITLE, FAKE_HANDLER_ID)
        ))
    }
}