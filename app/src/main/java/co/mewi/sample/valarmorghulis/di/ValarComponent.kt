package co.mewi.sample.valarmorghulis.di

import co.mewi.sample.valarmorghulis.view.MainActivity
import dagger.Component

@Component(modules = [ValarModule::class])
abstract class ValarComponent {

    abstract fun inject(mainActivity: MainActivity)

}