package co.mewi.sample.valarmorghulis

import android.app.Application
import co.mewi.sample.valarmorghulis.di.DaggerValarComponent
import co.mewi.sample.valarmorghulis.di.ValarComponent

open class MyApp : Application() {

    var valarComponent: ValarComponent = DaggerValarComponent.builder().build()

}