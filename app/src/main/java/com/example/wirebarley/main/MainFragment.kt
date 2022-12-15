package com.example.wirebarley.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.wirebarley.R

class MainFragment : Fragment(), MainContract.View, View.OnClickListener {

    companion object {
        fun newInstance(): MainFragment = MainFragment()
        private const val TAG = "SignUpFragment"
    }

    private lateinit var presenter: MainContract.Presenter

    private lateinit var tvSendCountry: TextView
    private lateinit var tvReceiveCountry: TextView
    private lateinit var tvInquiryDate: TextView
    private lateinit var tvExchangeRate: TextView
    private lateinit var tvMsgReceivePayment: TextView
    private lateinit var edSendPayment: EditText
    private lateinit var piker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val receiveCountryArray = resources.getStringArray(R.array.receive_country)
        tvSendCountry = root.findViewById(R.id.tv_send_country)
        tvReceiveCountry = root.findViewById(R.id.tv_receive_country)
        tvInquiryDate = root.findViewById(R.id.tv_inquiry_date)
        tvExchangeRate = root.findViewById(R.id.tv_exchange_rate)
        tvMsgReceivePayment = root.findViewById(R.id.tv_msg_receive_payment)
        edSendPayment = root.findViewById(R.id.ed_send_payment)
        piker = root.findViewById(R.id.picker_receive_country)

        piker.minValue = 0
        piker.maxValue = receiveCountryArray.size - 1
        piker.displayedValues = receiveCountryArray
        piker.setOnValueChangedListener { _, _, p2 ->
            tvReceiveCountry.text = receiveCountryArray[p2]
        }
        tvReceiveCountry.text = receiveCountryArray[0]

        root.findViewById<View>(R.id.btn_calculate).setOnClickListener(this)
        root.findViewById<View>(R.id.tv_receive_country).setOnClickListener(this)
        return root
    }

    override fun setInquiryDate(date: String) {
        tvInquiryDate.text = date
    }

    override fun setExchangeRate(rate: String, sendCountry: String) {
        tvExchangeRate.text = "$rate $sendCountry / USD"
    }

    override fun setMsgReceivePayment(payment: String, sendCountry: String) {
       tvMsgReceivePayment.text = "수취금액은 $payment $sendCountry 입니다."
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_calculate -> {
                val sendPayment = edSendPayment.text.toString().toInt()
                if(sendPayment > 10000) {
                    // TODO 에러 다이얼 로그.
                    Log.e(TAG, "너무 큰 금액입니다.")
                    return
                }-
                val textLength = tvReceiveCountry.text.toString().length
                val start = textLength - 4
                val end = textLength - 1
                presenter.calculateExchangeRate(tvReceiveCountry.text.toString().substring(start, end),sendPayment)
            }
            R.id.tv_receive_country -> {
                if(piker.visibility == View.GONE) {
                    piker.visibility = View.VISIBLE
                } else if (piker.visibility == View.VISIBLE) {
                    piker.visibility = View.GONE
                }
            }
        }
    }
}