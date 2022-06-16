package dark.composer.carpet.app

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import dark.composer.carpet.di.component.DaggerAppComponent

class App: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}