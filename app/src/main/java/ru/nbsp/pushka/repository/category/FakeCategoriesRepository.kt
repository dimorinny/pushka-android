package ru.nbsp.pushka.repository.category

import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import rx.Observable

/**
 * Created by Dimorinny on 08.03.16.
 */
class FakeCategoriesRepository : CategoriesRepository {

    companion object {
        private const val FAKE_CATEGORY_ID = "some_id"

        private const val FAKE_CATEGORY_TITLE1 = "Футбол"
        private const val FAKE_CATEGORY_TITLE2 = "Погода"
        private const val FAKE_CATEGORY_TITLE3 = "Курсы валют"

        private const val FAKE_CATEGORY_IMAGE = "http://www.hqwallpapers.ru/wallpapers/nature/tyomnoe-more-774x435.jpg"
        private const val FAKE_CATEGORY_IMAGE1 = "http://99px.ru/sstorage/53/2013/05/mid_69466_3527.jpg"
        private const val FAKE_CATEGORY_IMAGE2 = "http://hq-wallpapers.ru/wallpapers/8/hq-wallpapers_ru_nature_38893_1920x1080.jpg"
    }

    override fun getCategories(): Observable<List<PresentationCategory>> {
        return Observable.just(listOf(
                PresentationCategory(FAKE_CATEGORY_ID, FAKE_CATEGORY_TITLE1, FAKE_CATEGORY_IMAGE),
                PresentationCategory(FAKE_CATEGORY_ID, FAKE_CATEGORY_TITLE2, FAKE_CATEGORY_IMAGE1),
                PresentationCategory(FAKE_CATEGORY_ID, FAKE_CATEGORY_TITLE3, FAKE_CATEGORY_IMAGE2),
                PresentationCategory(FAKE_CATEGORY_ID, FAKE_CATEGORY_TITLE1, FAKE_CATEGORY_IMAGE),
                PresentationCategory(FAKE_CATEGORY_ID, FAKE_CATEGORY_TITLE2, FAKE_CATEGORY_IMAGE1),
                PresentationCategory(FAKE_CATEGORY_ID, FAKE_CATEGORY_TITLE2, FAKE_CATEGORY_IMAGE2),
                PresentationCategory(FAKE_CATEGORY_ID, FAKE_CATEGORY_TITLE2, FAKE_CATEGORY_IMAGE)
        ))
    }
}