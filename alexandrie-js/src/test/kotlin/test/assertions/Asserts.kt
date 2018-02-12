package test.assertions

import kotlin.test.assertTrue

fun assertContains(string: CharSequence, other: CharSequence) {
    assertTrue(string.contains(other), """expected "$string" to contain "$other" """)
}


fun assertNotContains(string: CharSequence, other: CharSequence) {
    assertTrue(!string.contains(other), """expected "$string" to not contain "$other" """)
}
