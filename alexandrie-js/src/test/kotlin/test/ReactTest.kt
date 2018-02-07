package test

import enzyme.Adapter
import enzyme.configure
import kotlin.test.BeforeTest

interface ReactTest {

    @BeforeTest
    fun configureEnzyme() {
        configure(object {
            val adapter = Adapter()
        })
    }

}
