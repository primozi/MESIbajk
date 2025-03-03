package software.ivancic.core.ui

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class ErrorDisplayWrapperKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenErrorTextIsNull_errorTextComposableIsNotShown() {
        composeTestRule.setContent {
            ErrorDisplayWrapper(errorText = null) {
                // no need for content
            }
        }

        composeTestRule.onNodeWithTag(ErrorDisplayWrapperTestTags.ERROR_TEXT).isNotDisplayed()
    }

    @Test
    fun givenErrorTextIsEmpty_errorTextComposableIsNotShown() {
        composeTestRule.setContent {
            ErrorDisplayWrapper(errorText = "") {
                // no need for content
            }
        }

        composeTestRule.onNodeWithTag(ErrorDisplayWrapperTestTags.ERROR_TEXT).isNotDisplayed()
    }

    @Test
    fun givenErrorTextIsPresent_errorTextComposableIsShown() {
        composeTestRule.setContent {
            ErrorDisplayWrapper(errorText = "Error text") {
                // no need for content
            }
        }

        composeTestRule.onNodeWithTag(ErrorDisplayWrapperTestTags.ERROR_TEXT).isDisplayed()
    }
}
