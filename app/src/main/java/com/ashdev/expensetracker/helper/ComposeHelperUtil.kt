import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ashdev.expensetracker.MainActivity
import com.ashdev.expensetracker.helper.FingerprintAuthManager
import com.ashdev.expensetracker.helper.logit

@Composable
fun BiometricAuth(onAuthSuccess: () -> Unit,
                  onAuthError: (String) -> Unit){
    val context = LocalContext.current
    val biometricManager = FingerprintAuthManager(context = context, onAuthSuccess = onAuthSuccess, onAuthError = onAuthError)
    val activity = context as MainActivity
    biometricManager.initialize(activity)
    biometricManager.authenticate()
}