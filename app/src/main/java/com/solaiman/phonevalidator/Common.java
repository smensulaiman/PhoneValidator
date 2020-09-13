package com.solaiman.phonevalidator;

public interface Common {
    interface OTPListener {
        void onOTPReceived(String otp);
    }
}