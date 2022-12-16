package com.example.wirebarley.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        edSendPayment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               // p0 ->  현재 입력된 문자열
                if(p0.toString().isEmpty()) return

                val sentPayment = p0.toString().toInt()
                if(sentPayment > 10000) {
                     edSendPayment.setText("10000")
                    // 다이얼 로그
                    AlertDialog.Builder(requireContext())
                        .setTitle("입력 오류")
                        .setMessage("송금액이 바르지 않습니다.")
                        .setPositiveButton("확인"
                        ) { _, _ -> }
                        .setCancelable(false)
                        .create()
                        .show()
                    return
                }
                val exchangePayment = presenter.getExchangedPayment(p0.toString().toInt())
                setMsgReceivePayment(exchangePayment, presenter.getSendCountry())
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        piker = root.findViewById(R.id.picker_receive_country)
        piker.minValue = 0
        piker.maxValue = receiveCountryArray.size - 1
        piker.displayedValues = receiveCountryArray
        piker.setOnValueChangedListener { _, _, p2 ->
            tvReceiveCountry.text = receiveCountryArray[p2]
            presenter.setSendCountry(p2)
            updateCurrencyData(p2)

            if(edSendPayment.text.toString().isEmpty()) {
                edSendPayment.setText("0")
            } else {
                edSendPayment.setText(edSendPayment.text.toString())
            }


        }
        tvReceiveCountry.text = receiveCountryArray[0]

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

    override fun getSendPayment(): Int {
        return edSendPayment.text.toString().toInt()
    }

    override fun errDialogAndFinish(title: String, errMsg: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(errMsg)
            .setPositiveButton("확인"
            ) { _, _ -> activity?.finish() }
            .setCancelable(false)
            .create()
            .show()
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
        presenter.start()
    }

    override fun updateCurrencyData(index: Int) {
        val currencyRate = presenter.getCurrencyRate(index)
        val sendCountry = presenter.getSendCountry()
        setExchangeRate(currencyRate, sendCountry)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.tv_receive_country -> {
                if(piker.visibility == View.GONE) {
                    piker.visibility = View.VISIBLE
                } else if (piker.visibility == View.VISIBLE) {
                    piker.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        presenter.compositeDisposableClear()
        super.onDestroy()
    }
}