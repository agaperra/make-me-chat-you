package com.agaperra.makemechatyou.di

import android.content.Context
import com.agaperra.makemechatyou.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.notifications.handler.NotificationConfig
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.pushprovider.firebase.FirebasePushDeviceGenerator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOfflinePluginFactory(@ApplicationContext applicationContext: Context) =
        StreamOfflinePluginFactory(
            config = Config(
                backgroundSyncEnabled = true,
                userPresence = true,
                persistenceEnabled = true,
                uploadAttachmentsNetworkType = UploadAttachmentsNetworkType.NOT_ROAMING,
                useSequentialEventHandler = false,
            ),
            appContext = applicationContext,
        )

    @Singleton
    @Provides
    fun provideNotificationConfig() = NotificationConfig(
        pushDeviceGenerators = listOf(FirebasePushDeviceGenerator())
    )

    @Singleton
    @Provides
    fun provideChatClient(
        @ApplicationContext context: Context,
        offlinePluginFactory: StreamOfflinePluginFactory,
        notificationConfig: NotificationConfig
    ) =
        ChatClient.Builder(BuildConfig.api_key, context).notifications(notificationConfig)
            .withPlugin(offlinePluginFactory).build()

}