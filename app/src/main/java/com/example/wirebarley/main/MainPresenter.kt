package com.example.wirebarley.main

import android.accounts.NetworkErrorException
import android.content.Context
import com.example.tordertask.utils.Util
import com.example.wirebarley.db.CurrencyRateData
import com.example.wirebarley.db.Currency
import com.example.wirebarley.db.SendCountry
import com.example.wirebarley.retrofit.CurrencyDataService
import com.example.wirebarley.retrofit.RetrofitClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.net.UnknownHostException

class MainPresenter(private val context: Context, private val view: MainFragment) :
    MainContract.Presenter {

    private var compositeDisposable = CompositeDisposable()
    private var sendCountry = SendCountry[0]
    private val appData = CurrencyRateData()

    init {
        view.setPresenter(this)
    }

    override fun start() {
        calculateExchangeRate()
    }

    override fun calculateExchangeRate() {
        val client = RetrofitClient.getInstance()
        val service = client.create(CurrencyDataService::class.java)

        service.getCurrencyRate(SEND_COUNTRY, RECEIVE_COUNTRY_LIST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(5)
            .subscribe({
                setPrefCurrencyRate(it)
                val timeStamp = it.timestamp
                val date = Util.timeStampToDate(timeStamp)
                view.setInquiryDate(date)
                view.updateCurrencyData(0)
            }, { throwable ->
                errProc(throwable)
            }).also { compositeDisposable.add(it) }
    }

    override fun compositeDisposableClear() {
        compositeDisposable.clear()
    }

    override fun setSendCountry(index: Int) {
        sendCountry = SendCountry[index]
    }

    override fun getSendCountry(): String {
        return sendCountry.toString()
    }

    override fun getExchangedPayment(sendPayment: Int): String {

        val currencyRate = when(sendCountry) {
            SendCountry.KRW -> appData.getRateUsdKrw(context)
            SendCountry.JPY -> appData.getRateUsdJpy(context)
            SendCountry.PHP -> appData.getRateUsdPhp(context)
        }
        return Util.addComma(sendPayment * currencyRate)
    }

    override fun getCurrencyRate(index: Int): String {
        return when(SendCountry[index]) {
            SendCountry.KRW -> {
                Util.addComma(appData.getRateUsdKrw(context))
            }
            SendCountry.JPY -> {
                Util.addComma(appData.getRateUsdJpy(context))
            }
            SendCountry.PHP -> {
                Util.addComma(appData.getRateUsdPhp(context))
            }
        }
    }

    private fun setPrefCurrencyRate(response: Currency) {
        val quotes = response.quotes
        appData.setRateUsdJpy(context, quotes.usdJpy)
        appData.setRateUsdKrw(context, quotes.usdKrw)
        appData.setRateUsdPhp(context, quotes.usdPhp)
    }

    private fun errProc(t: Throwable) {
        val title: String
        val errMsg: String
        when(t) {
            is NetworkErrorException, is UnknownHostException -> {
                title = "네크워크 에러"
                errMsg = "인터넷 연결 후 재시작해주세요"
            }
            is HttpException -> {
                title = "HTTP ${t.code()}"
                errMsg = when(t.code()) {
                    401 -> {
                        "인증과정에서 오류가 발생하였습니다."
                    }
                    404 -> {
                        "요청한 리소스를 찾을 수 없습니다."
                    }
                    503 -> {
                        "서버에 일시적으로 접근이 불가능 합니다."
                    }
                    else -> {
                        "에러를 확인해 주세요"
                    }
                }
            }
            else -> {
                title = "기타 에러"
                errMsg = "앱을 재시작해주세요"
            }
        }
        view.errDialogAndFinish(title, errMsg)
    }


    companion object {
        private const val TAG = "MainPresenter"
        private const val SEND_COUNTRY = "USD"
        private const val RECEIVE_COUNTRY_LIST = "KRW,JPY,PHP"
    }
}