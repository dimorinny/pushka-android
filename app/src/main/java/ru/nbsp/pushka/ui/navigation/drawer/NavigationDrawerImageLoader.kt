package ru.nbsp.pushka.ui.navigation.drawer

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.materialdrawer.icons.MaterialDrawerFont
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.squareup.picasso.Picasso
import ru.nbsp.pushka.R
import javax.inject.Inject

/**
 * Created by Dimorinny on 22.02.16.
 */
class NavigationDrawerImageLoader
    @Inject constructor(val picasso: Picasso) : AbstractDrawerImageLoader() {

    override fun set(imageView: ImageView?, uri: Uri?, placeholder: Drawable?) {
        picasso.load(uri).placeholder(placeholder).into(imageView)
    }

    override fun cancel(imageView: ImageView?) {
        picasso.cancelRequest(imageView)
    }

    override fun placeholder(ctx: Context?): Drawable? {
        return IconicsDrawable(ctx, MaterialDrawerFont.Icon.mdf_person).colorRes(R.color.grey).backgroundColorRes(R.color.white).sizeDp(56).paddingDp(16);
    }
}