package com.example.bhoomicam.view.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bhoomicam.R
import com.example.bhoomicam.databinding.FragmentLoginBinding
import com.example.bhoomicam.view.dashboard.DashboardActivity
import com.example.bhoomicam.view.dashboard.MapActivity
import com.example.bhoomicam.view.utils.Datastore
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.TimeUnit


class LoginFragment : Fragment() {
    // variable for FirebaseAuth class
    private var mAuth: FirebaseAuth? = null

    // variable for our text input
    // field for phone and OTP.
    private var edtPhone: EditText? = null  // variable for our text input

    // field for phone and OTP.
    private var edtOTP: EditText? = null

    // buttons for generating OTP and verifying OTP
    private var verifyOTPBtn: Button? = null // buttons for generating OTP and verifying OTP
    private var generateOTPBtn: Button? = null

    // string for storing our verification ID
    private var verificationId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.SignUpPage.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUp)
        }
        binding.LoginButton.setOnClickListener {
            startActivity(Intent(requireContext(),DashboardActivity::class.java))
        }
        // below line is for getting instance
        // of our FirebaseAuth.

        // below line is for getting instance
        // of our FirebaseAuth.
        mAuth = FirebaseAuth.getInstance()

        // initializing variables for button and Edittext.

        // initializing variables for button and Edittext.
        edtPhone =binding.LoginName
        edtOTP = binding.LoginPassword
        verifyOTPBtn = binding.LoginButton
        generateOTPBtn = binding.SendOTpLogin

        // setting onclick listener for generate OTP button.

        // setting onclick listener for generate OTP button.
        generateOTPBtn!!.setOnClickListener {
            // below line is for checking whether the user
            // has entered his mobile number or not.
            if (TextUtils.isEmpty(edtPhone!!.text.toString())) {
                // when mobile number text field is empty
                // displaying a toast message.
                Toast.makeText(
                    requireContext(),
                    "Please enter a valid phone number.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                // if the text field is not empty we are calling our
                // send OTP method for getting OTP from Firebase.
                val phone = "+91" + edtPhone!!.text.toString()
                sendVerificationCode(phone)
            }
        }

        // initializing on click listener
        // for verify otp button

        // initializing on click listener
        // for verify otp button

        verifyOTPBtn!!.setOnClickListener {
            // validating if the OTP text field is empty or not.
            if (TextUtils.isEmpty(edtOTP!!.text.toString())) {
                // if the OTP text field is empty display
                // a message to user to enter OTP
                Toast.makeText(requireContext(), "Please enter OTP", Toast.LENGTH_SHORT).show()
            } else {
                // if OTP field is not empty calling
                // method to verify the OTP.
                verifyCode(edtOTP!!.text.toString())
            }
        }
        return binding.root
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    lifecycleScope.launch {
                        var datastore= Datastore(requireContext())
                        datastore.changeLoginState(true)
                    }
                    activity?.finish()
                    startActivity(Intent(requireContext(),MapActivity::class.java))
                } else {
                    Toast.makeText(
                        requireContext(),
                        task.exception!!.message,
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
    }


    private fun sendVerificationCode(number: String) {
        // this method is used for getting
        // OTP on user phone number.
        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // callback method is called on Phone auth provider.
    private val   // initializing our callbacks for on
    // verification callback method.
            mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            // below method is used when
            // OTP is sent from Firebase
            override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s
            }

            // this method is called when user
            // receive OTP from Firebase.
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                // below line is used for getting OTP code
                // which is sent in phone auth credentials.
                val code = phoneAuthCredential.smsCode

                // checking if the code
                // is null or not.
                if (code != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP edittext field.
                    edtOTP!!.setText(code)

                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifycode method.
                    verifyCode(code)
                }
            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                // displaying error message with firebase exception.
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }
        }

    // below method is use to verify code from Firebase.
    private fun verifyCode(code: String) {
        // below line is used for getting
        // credentials from our verification id and code.
        try {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            // after getting credential we are
            // calling sign in method.
            signInWithCredential(credential)
        }
       catch (e:Exception)
       {
           Toast.makeText(requireContext(), "Invalid Credential", Toast.LENGTH_SHORT).show()
       }


    }
}