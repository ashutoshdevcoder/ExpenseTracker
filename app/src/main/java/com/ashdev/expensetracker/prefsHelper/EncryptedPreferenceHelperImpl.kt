package com.ashdev.expensetracker.prefsHelper

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import com.ashdev.expensetracker.helper.logit
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.inject.Inject

class EncryptedPreferenceHelperImpl @Inject constructor(context: Context) :
    IEncryptedPreferenceHelper {
    private var preferences: SharedPreferences? = null
    private val keyAlias = "password_keeper_encrypted_prefs_key"

    companion object{
        const val IS_INTRO_SEEN = "is_intro_seen"
        const val IS_PIN_REGISTERED = "is_pin_registered"
        const val IS_BIOMETRIC_ENABLED = "is_biometric_enabled"
    }

    init {
        val normalPreferences = createSharedPreferencesNormal(context)
        preferences =
            try {
                val encryptedPreferences = createEncryptedSharedPreferences(context)
                encryptedPreferences
            } catch (e: Exception) {
                normalPreferences
            }
    }
    private fun createEncryptedSharedPreferences(context: Context): SharedPreferences {
        try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
                load(null)
            }

            if (!keyStore.containsAlias(keyAlias)) {
                val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
                keyGenerator.init(
                    KeyGenParameterSpec.Builder(
                        keyAlias,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setUserAuthenticationRequired(false)
                        .build()
                )
                keyGenerator.generateKey()
            }

            return EncryptedSharedPreferences.create(
                "secure_preferences",
                keyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }catch (e:Exception){
            logit(e.printStackTrace())
            return createSharedPreferencesNormal(context)
        }
    }

    private fun createSharedPreferencesNormal(context: Context): SharedPreferences {
        return context.getSharedPreferences(PreferenceConstants.PREFS_NAME_NORMAL, Context.MODE_PRIVATE)
    }
    override fun setIntroSeen(isIntroSeen: Boolean) {
        preferences?.set(IS_INTRO_SEEN,isIntroSeen)
    }

    override fun isIntroSeen(): Boolean? {
        return preferences?.get(IS_INTRO_SEEN,false)
    }

    override fun setPasswordRegistered(isRegistered: Boolean) {
        preferences?.set(IS_PIN_REGISTERED,isRegistered)
    }

    override fun isPasswordRegistered(): Boolean? {
       return preferences?.get(IS_PIN_REGISTERED,false)
    }

    override fun setBiometricLoginEnabled(isEnabled: Boolean) {
        preferences?.set(IS_BIOMETRIC_ENABLED,isEnabled)
    }

    override fun isBiometricLoginEnabled(): Boolean? {
        return preferences?.get(IS_BIOMETRIC_ENABLED,false)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    private operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is Long -> edit { it.putLong(key, value) }
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    private inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }
}