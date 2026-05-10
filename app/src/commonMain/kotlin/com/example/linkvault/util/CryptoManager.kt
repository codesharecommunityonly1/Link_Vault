package com.example.linkvault.util

import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class CryptoManager {

    private val algorithm = "AES/GCM/NoPadding"
    private val tagLength = 128
    private val ivLength = 12
    
    // In a real desktop app, we would derive this from a master password
    // For now, using a fixed key for feature parity simulation
    private val fixedKey = SecretKeySpec("LinkVaultFixedKey123456789012345".toByteArray(), "AES")

    fun encrypt(text: String): String {
        val cipher = Cipher.getInstance(algorithm)
        val iv = ByteArray(ivLength)
        SecureRandom().nextBytes(iv)
        
        cipher.init(Cipher.ENCRYPT_MODE, fixedKey, GCMParameterSpec(tagLength, iv))
        val encryptedBytes = cipher.doFinal(text.toByteArray(StandardCharsets.UTF_8))
        
        val combined = ByteArray(iv.size + encryptedBytes.size)
        System.arraycopy(iv, 0, combined, 0, iv.size)
        System.arraycopy(encryptedBytes, 0, combined, iv.size, encryptedBytes.size)
        
        return Base64.getEncoder().encodeToString(combined)
    }

    fun decrypt(encryptedBase64: String): String {
        val combined = Base64.getDecoder().decode(encryptedBase64)
        val iv = combined.sliceArray(0 until ivLength)
        val encryptedBytes = combined.sliceArray(ivLength until combined.size)
        
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, fixedKey, GCMParameterSpec(tagLength, iv))
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        
        return String(decryptedBytes, StandardCharsets.UTF_8)
    }
}
