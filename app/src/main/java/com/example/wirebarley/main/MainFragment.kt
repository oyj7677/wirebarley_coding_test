package com.example.wirebarley.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.wirebarley.R

class MainFragment : Fragment(), MainContract.View {

    companion object {
        fun newInstance(): MainFragment = MainFragment()
        private const val TAG = "SignUpFragment"
    }

    private lateinit var presenter: MainContract.Presenter

    private lateinit var sendSpinner: Spinner
    private lateinit var receiveSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        sendSpinner = root.findViewById(R.id.spinner_send_country)
        receiveSpinner = root.findViewById(R.id.spinner_receive_country)

        ArrayAdapter.createFromResource(requireContext(), R.array.send_country, android.R.layout.simple_spinner_item )
            .also { adapter ->
                sendSpinner.adapter = adapter
            }

        ArrayAdapter.createFromResource(requireContext(), R.array.receive_country, android.R.layout.simple_spinner_item)
            .also { adapter ->
                receiveSpinner.adapter = adapter
            }

        return root
    }


    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }
}