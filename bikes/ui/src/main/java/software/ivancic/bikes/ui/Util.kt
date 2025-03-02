package software.ivancic.bikes.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import software.ivancic.bikes.domain.model.Department
import software.ivancic.bikes.domain.model.Intent

@Composable
fun Department.toReadableString(): String = stringResource(
    when (this) {
        Department.DEVELOPMENT -> R.string.development
        Department.SALES -> R.string.sales
        Department.MARKETING -> R.string.marketing
        Department.PRODUCTION -> R.string.production
    }
)

@Composable
fun Intent.toReadableString(): String = stringResource(
    when (this) {
        Intent.PRIVATE -> R.string.intent_private
        Intent.BUSINESS -> R.string.intent_business
    }
)
