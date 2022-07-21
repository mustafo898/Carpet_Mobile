package dark.composer.carpet.presentation.dialog

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import dark.composer.carpet.R
import dark.composer.carpet.databinding.DialogUpdateProfileBinding
import dark.composer.carpet.presentation.fragment.profile.ProfileViewModel
import kotlinx.coroutines.launch

class UpdateProfileDialog(content: Context, name: String, lastname: String) : AlertDialog(content) {
    var binding = DialogUpdateProfileBinding.inflate(layoutInflater)

    private var updateListener: ((name: String, surname: String, password: String, phone: String,role:String) -> Unit)? =
        null

    fun setOnAddListener(f: (name: String, surname: String, password: String, phone: String, role:String) -> Unit) {
        updateListener = f
    }

    private var phoneVisible = false

    fun setPhoneVisible(phoneVisible: Boolean) {
        this.phoneVisible = phoneVisible
        if (phoneVisible) {
            binding.phoneNumberInput.visibility = View.VISIBLE
        } else {
            binding.phoneNumberInput.visibility = View.GONE
        }
    }

    private var title: String = ""
    fun setTitle(title: String) {
        this.title = title
        binding.title.text = title
    }

    private var customer = false
    private var employee = false

    init {
        window?.setBackgroundDrawable(ColorDrawable(0))

        window?.setWindowAnimations(R.style.AnimationForDialog)
        var statusTxt = ""

        binding.customerRadio.isChecked = true

        if (binding.customerRadio.isChecked){
            statusTxt = "CUSTOMER"
        }else if (binding.employeeRadio.isChecked){
            statusTxt = "EMPLOYEE"
        }

        var n = false
        var s = false
        binding.name.setText(name)
        binding.lastName.setText(lastname)

        binding.name.addTextChangedListener {
            if (validName(it.toString())) {
                n = true
                binding.nameInput.isHelperTextEnabled = false
                binding.acceptFB.isClickable = true
            } else {
                binding.acceptFB.isClickable = false
            }
        }

        binding.lastName.addTextChangedListener {
            if (validSurname(it.toString())) {
                s = true
                binding.lastnameInput.isHelperTextEnabled = false
                binding.acceptFB.isClickable = true
            } else {
                binding.acceptFB.isClickable = false

            }
        }

        binding.password.addTextChangedListener {
            if (validPassword(it.toString())) {
                s = true
                binding.passwordInput.isHelperTextEnabled = false
                binding.acceptFB.isClickable = true
            } else {
                binding.acceptFB.isClickable = false
            }
        }

        binding.phoneNumber.addTextChangedListener {
            if (validPhone(it.toString())) {
                s = true
                binding.phoneNumberInput.isHelperTextEnabled = false
                binding.acceptFB.isClickable = true
            } else {
                binding.acceptFB.isClickable = false
            }
        }

        binding.acceptFB.setOnClickListener {
            updateListener?.invoke(
                binding.name.text.toString(),
                binding.lastName.text.toString(),
                binding.password.text.toString(),
                binding.phoneNumber.text.toString(),
                statusTxt
            )
        }

        binding.cancelFB.setOnClickListener {
            dismiss()
        }
        setView(binding.root)
    }

    private fun validName(name: String): Boolean {
        if (name.isEmpty()) {
            binding.nameInput.helperText = "Name must be entered"
            return false
        } else if (name.length < 4) {
            binding.nameInput.helperText = "Minimum 4 Characters Name"
            return false
        } else {
            return true
        }
    }

    private fun validSurname(surName: String): Boolean {
        if (surName.isEmpty()) {
            binding.lastnameInput.helperText = "Last Name must be entered"
            return false
        } else if (surName.length <= 4) {
            binding.lastnameInput.helperText = "Minimum 4 Characters LastName"
            return false
        } else {

            return true
        }
    }

    private fun validPassword(password: String): Boolean {
        if (password.length <= 6) {
            binding.passwordInput.helperText = "Minimum 6 Character Password"
            return false
        } else if (!password.matches(".*[A-Z].*".toRegex())) {
            binding.passwordInput.helperText = "Must Contain 1 Upper-case Character"
            return false
        } else if (!password.matches(".*[a-z].*".toRegex())) {
            binding.passwordInput.helperText = "Must Contain 1 Lower-case Character"
            return false
        } else if (!password.matches(".*[@#\$%^_].*".toRegex())) {
            binding.passwordInput.helperText = "Must Contain 1 Special Character (@#\\\$%^_)"
            return false
        } else {
            return true
        }
    }

    private fun validPhone(phone: String): Boolean {
        if (phone.isEmpty()) {
            binding.phoneNumberInput.helperText = "Phone Number must be entered"
            return false
        } else if (phone.length != 9) {
            binding.phoneNumberInput.helperText = "Please Enter Correct Phone Number"
            return false
        } else {
            return true
        }
    }
}