package env

import kotlin.test.Test
import kotlin.test.assertNotNull


class EnvironmentTest {

    @Test
    fun containsBackendUrl() {
        assertNotNull(environment.backRootUrl)
    }
}
