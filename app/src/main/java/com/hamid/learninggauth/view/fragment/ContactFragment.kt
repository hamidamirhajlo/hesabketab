package com.hamid.learninggauth.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hamid.learninggauth.R
import kotlinx.android.synthetic.main.fragment_contact.*

class ContactFragment : Fragment(R.layout.fragment_contact) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_tel.setOnClickListener {
            val tel = getString(R.string.phone)
            val intent = Intent(Intent.ACTION_DIAL,Uri.parse("tel:$tel"))
            requireActivity().startActivity(intent)
        }

        tv_email.setOnClickListener {
            val email = getString(R.string.email)
           composeEmail(arrayOf(email),getString(R.string.app_name))
        }

    }

    @SuppressLint("QueryPermissionsNeeded")
    fun composeEmail(addresses: Array<String?>?, subject: String?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }
}