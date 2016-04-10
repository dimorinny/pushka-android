package ru.nbsp.pushka.presentation.source.feed

import ru.nbsp.pushka.presentation.core.base.BaseView
import ru.nbsp.pushka.presentation.core.model.source.PresentationSource
import ru.nbsp.pushka.presentation.core.state.State

/**
 * Created by Dimorinny on 24.02.16.
 */
interface SourceView : BaseView {
    fun setSources(sources: List<PresentationSource>)
    fun setState(state: State)
    fun openSubscribeScreen(source: PresentationSource)
}