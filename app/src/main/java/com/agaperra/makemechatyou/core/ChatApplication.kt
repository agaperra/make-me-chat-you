package com.agaperra.makemechatyou.core

import android.app.Application
import com.agaperra.makemechatyou.BuildConfig.api_key
import dagger.hilt.android.HiltAndroidApp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory

/**
 * Chat application
 *
 * @constructor Create empty Chat application
 */
@HiltAndroidApp
class ChatApplication : Application() {


    /**
     * On create
     *
     * This method create chat client with offline cache factory
     */
    override fun onCreate() {
        super.onCreate()

        val offlinePluginFactory = StreamOfflinePluginFactory(
            config = Config(
                backgroundSyncEnabled = true,
                userPresence = true,
                persistenceEnabled = true,
                uploadAttachmentsNetworkType = UploadAttachmentsNetworkType.NOT_ROAMING,
                useSequentialEventHandler = false,
            ),
            appContext = applicationContext,
        )

        ChatClient.Builder(api_key, applicationContext).withPlugin(offlinePluginFactory).build()
    }

}