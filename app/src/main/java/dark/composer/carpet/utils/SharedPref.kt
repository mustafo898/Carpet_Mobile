package dark.composer.carpet.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPref(context: Context) {

    private var preferences: SharedPreferences =
        context.getSharedPreferences("APP_PREFS_NAME", MODE_PRIVATE)

    private lateinit var editor: SharedPreferences.Editor

    fun setToken(token: String) {
        editor = preferences.edit()
        editor.putString("TOKEN", token)
        editor.apply()
    }

    fun getToken() = preferences.getString("TOKEN", "")

    fun setName(name: String) {
        editor = preferences.edit()
        editor.putString("NAME", name)
        editor.apply()
    }

    fun getName() = preferences.getString("NAME", "")

    fun setSurName(surName: String) {
        editor = preferences.edit()
        editor.putString("SURNAME", surName)
        editor.apply()
    }

    fun getSurName() = preferences.getString("SURNAME", "")

    fun setImage(image: String) {
        editor = preferences.edit()
        editor.putString("Image", image)
        editor.apply()
    }

    fun getImage() = preferences.getString("Image", "")

    fun setPhoneNumber(phoneNumber: String) {
        editor = preferences.edit()
        editor.putString("PHONE_NUMBER", phoneNumber)
        editor.apply()
    }

    fun getPhoneNumber() = preferences.getString("PHONE_NUMBER", "")

    fun setRole(role: String) {
        editor = preferences.edit()
        editor.putString("ROLE", role)
        editor.apply()
    }
    fun getRole() = preferences.getString("ROLE", "")
}
