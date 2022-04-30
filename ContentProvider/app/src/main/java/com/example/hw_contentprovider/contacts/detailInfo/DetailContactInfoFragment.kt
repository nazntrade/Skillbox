package com.example.hw_contentprovider.contacts.detailInfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.hw_contentprovider.R
import com.example.hw_contentprovider.databinding.FragmentDetailInfoBinding

class DetailContactInfoFragment : Fragment(R.layout.fragment_detail_info) {

    private val args: DetailContactInfoFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailInfoBinding.bind(requireView())

        binding.detailNameTextView.text = args.currentContact.name
        binding.detailPhonesTextView.text = args.currentContact.phones.joinToString("\n")
        binding.detailEmailTextView.text = args.currentContact.email.joinToString  ("\n")
    }
}