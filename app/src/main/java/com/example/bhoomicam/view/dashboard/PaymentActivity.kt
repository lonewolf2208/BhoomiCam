package com.example.bhoomicam.view.dashboard

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.bhoomicam.R
import com.razorpay.Checkout
import com.razorpay.PayloadHelper
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() , PaymentResultListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        /*
         * To ensure faster loading of the Checkout form,
         * call this method as early as possible in your checkout flow
         * */

        val co = Checkout()
        Checkout.preload(applicationContext)
        // apart from setting it in AndroidManifest.xml, keyId can also be set
        // programmatically during runtime
        co.setKeyID("rzp_test_pPyQDwFu6wozYU")
        val payloadHelper = PayloadHelper("INR", 100, "order_XXXXXXXXX")
        payloadHelper.name = "Gaurav Kumae"
        payloadHelper.description = "Description"
        payloadHelper.prefillEmail = "gaurav.kumar@example.com"
        payloadHelper.prefillContact = "9999999999"
        payloadHelper.prefillCardNum = "4111111111111111"
        payloadHelper.prefillCardCvv = "111"
        payloadHelper.prefillCardExp = "11/24"
        payloadHelper.prefillMethod = "netbanking"
        payloadHelper.prefillName = "BhoomiCam"
        payloadHelper.sendSmsHash = true
        payloadHelper.retryMaxCount = 4
        payloadHelper.retryEnabled = true
        payloadHelper.color = "#000000"
        payloadHelper.allowRotation = true
        payloadHelper.rememberCustomer = true
        payloadHelper.timeout = 10
        payloadHelper.redirect = true
        payloadHelper.recurring = "1"
        payloadHelper.subscriptionCardChange = true
        payloadHelper.customerId = "cust_XXXXXXXXXX"
        payloadHelper.callbackUrl = "https://accepts-posts.request"
        payloadHelper.subscriptionId = "sub_XXXXXXXXXX"
        payloadHelper.modalConfirmClose = true
        payloadHelper.backDropColor = "#ffffff"
        payloadHelper.hideTopBar = true
        payloadHelper.notes = JSONObject("{\"remarks\":\"Discount to cusomter\"}")
        payloadHelper.readOnlyEmail = true
        payloadHelper.readOnlyContact = true
        payloadHelper.readOnlyName = true
        payloadHelper.image = "https://www.razorpay.com"
        // these values are set mandatorily during object initialization. Those values can be overridden like this
        payloadHelper.amount = 100
        payloadHelper.currency = "INR"
        payloadHelper.orderId = "order_XXXXXXXXXXXXXX"
        startPayment()
    }

    private fun startPayment() {

        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name", "Razorpay Corp")
            options.put("description", "Demoing Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000")//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email", "gaurav.kumar@example.com")
            prefill.put("contact", "9876543210")

            options.put("prefill", prefill)
            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "PaymentSuccessfull", Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("dsada",p1.toString())
        Toast.makeText(this, p1.toString(), Toast.LENGTH_LONG).show()
    }
}