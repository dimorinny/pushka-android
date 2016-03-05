package ru.nbsp.pushka.presentation.source.feed

import ru.nbsp.pushka.data.model.source.Source
import ru.nbsp.pushka.presentation.core.base.BaseView

/**
 * Created by Dimorinny on 24.02.16.
 */
interface SourceView : BaseView {
    fun setSources(sources: List<Source>)
}