package com.example.demo.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.springframework.beans.factory.annotation.Value; // ✅ FIXED
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/otp")
public class OtpController {

    @Value("${twilio.sid}")
    private String sid;

    @Value("${twilio.token}")
    private String token;

    private static final String TWILIO_NUMBER = "+12182741724";

    private Map<String, String> otpStore = new HashMap<>();

    @PostMapping("/send/{phone}")
    public String sendOtp(@PathVariable String phone) {

        Twilio.init(sid, token); // ✅ FIXED

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStore.put(phone, otp);

        Message.creator(
                new com.twilio.type.PhoneNumber("+91" + phone),
                new com.twilio.type.PhoneNumber(TWILIO_NUMBER),
                "Your OTP is: " + otp
        ).create();

        return "OTP sent successfully";
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String phone, @RequestParam String otp) {

        if (otpStore.containsKey(phone) && otpStore.get(phone).equals(otp)) {
            otpStore.remove(phone);
            return "OTP Verified";
        }

        return "Invalid OTP";
    }
}