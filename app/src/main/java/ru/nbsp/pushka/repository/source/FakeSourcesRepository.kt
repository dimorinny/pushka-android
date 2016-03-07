package ru.nbsp.pushka.repository.source

import ru.nbsp.pushka.presentation.core.model.source.PresentationParam
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
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

    override fun getSources(): Observable<List<PresentationSource>> {
        return Observable.just(listOf(
                PresentationSource(FAKE_ID, ArrayList<PresentationParam>(), FAKE_SOURCE_TITLE, FAKE_SOURCE_SUBTITLE, FAKE_HANDLER_ID),
                PresentationSource(FAKE_ID, ArrayList<PresentationParam>(), FAKE_SOURCE_TITLE2, FAKE_SOURCE_SUBTITLE2, FAKE_HANDLER_ID),
                PresentationSource(FAKE_ID, ArrayList<PresentationParam>(), FAKE_SOURCE_TITLE3, FAKE_SOURCE_SUBTITLE3, FAKE_HANDLER_ID),
                PresentationSource(FAKE_ID, ArrayList<PresentationParam>(), FAKE_SOURCE_TITLE, FAKE_SOURCE_SUBTITLE, FAKE_HANDLER_ID),
                PresentationSource(FAKE_ID, ArrayList<PresentationParam>(), FAKE_SOURCE_TITLE2, FAKE_SOURCE_SUBTITLE2, FAKE_HANDLER_ID),
                PresentationSource(FAKE_ID, ArrayList<PresentationParam>(), FAKE_SOURCE_TITLE3, FAKE_SOURCE_SUBTITLE3, FAKE_HANDLER_ID),
                PresentationSource(FAKE_ID, ArrayList<PresentationParam>(), FAKE_SOURCE_TITLE, FAKE_SOURCE_SUBTITLE, FAKE_HANDLER_ID)
        ))
    }
}