package dark.composer.carpet.data.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dark.composer.carpet.presentation.activity.MainActivity

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = [ViewModelsModule::class, MainFragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}