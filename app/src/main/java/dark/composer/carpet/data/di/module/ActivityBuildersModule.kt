package dark.composer.carpet.data.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dark.composer.carpet.presentation.activity.MainActivity
import dark.composer.carpet.presentation.activity.SplashScreenActivity

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = [ViewModelsModule::class, MainFragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ViewModelsModule::class, MainFragmentBuildersModule::class])
    abstract fun contributeSplashScreenActivity(): SplashScreenActivity
}