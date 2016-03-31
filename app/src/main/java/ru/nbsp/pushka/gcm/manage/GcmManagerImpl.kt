package ru.nbsp.pushka.gcm.manage

import android.content.Context
import eu.inloop.easygcm.EasyGcm

/**
 * Created by Dimorinny on 31.03.16.
 */
class GcmManagerImpl(val context: Context) : GcmManager {

    override fun init() {
        EasyGcm.init(context)
    }

    override fun clear() {
        EasyGcm.removeRegistrationId(context)
    }
}