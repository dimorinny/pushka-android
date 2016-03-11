package ru.nbsp.pushka.repository.category

import ru.nbsp.pushka.mapper.presentation.source.PresentationCategoryMapper
import ru.nbsp.pushka.network.service.PushkaSourceService
import ru.nbsp.pushka.presentation.core.model.source.PresentationCategory
import ru.nbsp.pushka.util.SchedulersUtils
import rx.Observable
import java.util.*

/**
 * Created by Dimorinny on 11.03.16.
 */
class ApiCategoriesRepository(
        val apiPushka: PushkaSourceService,
        val categoryMapper: PresentationCategoryMapper,
        val schedulersUtils: SchedulersUtils) : CategoriesRepository {

    override fun getCategories(): Observable<List<PresentationCategory>> {
        return apiPushka
                .getCategories()
                .map {
                    var result = ArrayList<PresentationCategory>()
                    for (category in it.categories) {
                        result.add(categoryMapper.fromNetworkCategory(category))
                    }
                    result
                }
                .compose(schedulersUtils.applySchedulers<List<PresentationCategory>>())
    }
}