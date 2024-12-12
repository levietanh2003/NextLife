package com.fatherofapps.androidbase.ui.customer.myaccount

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.databinding.ActivityWalletBinding
import com.fatherofapps.androidbase.ui.customer.payment.PaymentViewModel
import com.fatherofapps.androidbase.ui.customer.payment.TransactionActivity
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject
import vn.momo.momo_partner.AppMoMoLib
import java.util.UUID

@AndroidEntryPoint
class WalletActivity : BaseActivity() {
    private lateinit var dataBinding: ActivityWalletBinding
    private val viewModelPayment: PaymentViewModel by viewModels()

    // payment momo
    private val merchantName = "NextLife"
    private val merchantCode = "MOMOC2IC20220510"

    // gia cua goi cuoc
    private lateinit var totalPrice : String
    // random id chuyen tien thay cho id don hang
    private lateinit var idPayment : UUID


    //    private val merchantNameLabel = "Nhà cung cấp"
    private val description = "Hỗ trợ ADS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        // Initialize MoMo SDK
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT)

        viewModelPayment.balance.observe(this){
            dataBinding.tvMainBalance.text = it.toString()
        }

        dataBinding.btnBack.setOnClickListener {
            finish()
        }

        dataBinding.btnAddMoney.setOnClickListener {
            // process add money & testing momo
            idPayment = UUID.randomUUID()
            totalPrice = "50000" // Thay đổi giá trị theo nhu cầu của bạn
            requestPaymentMoMo(idPayment.toString())

        }


        dataBinding.btnHistoryPayment.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            startActivity(intent)

        }
    }

    //Get token through MoMo app
    private fun requestPaymentMoMo(idPayment: String){
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT)
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN)

        // chuyen doi kieu du lieu
        val eventValue: MutableMap<String, Any> = HashMap()
        //client Required
        eventValue["merchantname"] =
            merchantName //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue["merchantcode"] =
            merchantCode //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue["amount"] = totalPrice //Kiểu integer
        eventValue["orderId"] =
            idPayment //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue["orderLabel"] = idPayment //gán nhãn

        //client Optional - bill info
        eventValue["merchantnamelabel"] = "Online Payment" //gán nhãn
        eventValue["fee"] = "0" //Kiểu integer
        eventValue["description"] = description //mô tả đơn hàng - short description

        //client extra data
        eventValue["requestId"] = merchantCode + "merchant_billId_" + System.currentTimeMillis()
        eventValue["partnerCode"] = merchantCode
        //Example extra data

        Log.d("TOKEN", "Token Momo Payment : $merchantCode")
        val objExtraData = JSONObject()
        try {
            objExtraData.put("site_code", "008")
            objExtraData.put("site_name", "CGV Cresent Mall")
            objExtraData.put("screen_code", 0)
            objExtraData.put("screen_name", "Special")
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3")
            objExtraData.put("movie_format", "2D")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        eventValue["extraData"] = objExtraData.toString()

        eventValue["extra"] = ""
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue)

    }

    // Xử lý kết quả từ MoMo
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if (data != null) {
                when (data.getIntExtra("status", -1)) {
                    0 -> {
                        // Thanh toán thành công, tiến hành đẩy đơn hàng vào cơ sở dữ liệu
                        viewModelPayment.postPayment(totalPrice,"MOMO")
                        Log.d("TotalPrice", totalPrice)
                        showNotifyDialog("Thanh toán thành công","Thông báo","OK")


                    }
                    1 -> {
                        // Thanh toán thất bại
                        showNotifyDialog("Thanh toán thất bại","Thông báo","OK")
                    }
                    else -> {260703
                        // Các trường hợp khác
                        showNotifyDialog("Chưa hoàn tất thanh toán","Thông báo","OK")

                    }
                }
            } else {
                // Khi data trả về từ MoMo là null
                showNotifyDialog("Thanh toán thất bại","Thông báo","OK")

            }
        } else {
            // Khi requestCode hoặc resultCode không phù hợp
            showNotifyDialog("Thanh toán thất bại","Thông báo","OK")
        }
    }
}