package com.fatherofapps.androidbase.ui.customer.myaccount

import android.content.Intent
import android.os.Bundle
import com.fatherofapps.androidbase.base.activities.BaseActivity
import com.fatherofapps.androidbase.databinding.ActivityWalletBinding
import com.fatherofapps.androidbase.ui.customer.payment.TransactionActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletActivity : BaseActivity() {
    private lateinit var dataBinding: ActivityWalletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        dataBinding.btnHistoryPayment.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            startActivity(intent)

        }

    }
}