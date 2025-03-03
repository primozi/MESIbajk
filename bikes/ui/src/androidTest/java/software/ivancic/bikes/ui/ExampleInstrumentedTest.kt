package software.ivancic.bikes.ui

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    /**
     * Here a bunch of tests for checking if the correct fields are shown for each particular
     * screen and if the data is indeed correct.
     * Part of navigation tests should be here as well (necessarily the same class, though)
     */
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("software.ivancic.bikes.ui.test", appContext.packageName)
    }
}
