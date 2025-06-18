package com.teamsolply.solply.datastore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.datastore.core.Serializer
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

@OptIn(InternalSerializationApi::class)
class SolplySecureDataStoreSerializer : Serializer<SolplyLocalData> {
    companion object {
        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val KEY_ALIAS = "data-store-key"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val IV_SIZE = 12
    }

    private val keyStore: KeyStore by lazy {
        KeyStore.getInstance(KEYSTORE_PROVIDER).apply {
            load(null)
            if (!containsAlias(KEY_ALIAS)) {
                createKey()
            }
        }
    }

    private fun createKey() {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            KEYSTORE_PROVIDER
        )
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    override val defaultValue: SolplyLocalData
        get() = SolplyLocalData()

    override suspend fun readFrom(input: InputStream): SolplyLocalData {
        return try {
            val encryptedDataWithIv = input.readBytes()
            if (encryptedDataWithIv.size < IV_SIZE) {
                return defaultValue
            }

            val iv = encryptedDataWithIv.copyOfRange(0, IV_SIZE)
            val encryptedData = encryptedDataWithIv.copyOfRange(IV_SIZE, encryptedDataWithIv.size)

            val cipher = Cipher.getInstance(TRANSFORMATION)
            val key = keyStore.getKey(KEY_ALIAS, null) as SecretKey
            val spec = GCMParameterSpec(128, iv)
            cipher.init(Cipher.DECRYPT_MODE, key, spec)

            val decryptedBytes = cipher.doFinal(encryptedData)
            Json.decodeFromString(
                deserializer = SolplyLocalData.serializer(),
                string = decryptedBytes.decodeToString(),
            )
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: SolplyLocalData, output: OutputStream) {
        try {
            val serializedData = Json.encodeToString(
                serializer = SolplyLocalData.serializer(),
                value = t,
            )

            val cipher = Cipher.getInstance(TRANSFORMATION)
            val key = keyStore.getKey(KEY_ALIAS, null) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key)

            val iv = cipher.iv
            val encryptedData = cipher.doFinal(serializedData.toByteArray())

            output.write(iv + encryptedData)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}