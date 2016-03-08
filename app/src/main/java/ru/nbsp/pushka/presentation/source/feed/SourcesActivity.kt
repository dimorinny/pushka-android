package ru.nbsp.pushka.presentation.source.feed

import android.support.v4.app.Fragment
import ru.nbsp.pushka.presentation.core.base.OneFragmentActivity

/**
 * Created by Dimorinny on 08.03.16.
 */
class SourcesActivity : OneFragmentActivity() {

    override fun createFragment(): Fragment {
        return SourcesFragment()
    }
}