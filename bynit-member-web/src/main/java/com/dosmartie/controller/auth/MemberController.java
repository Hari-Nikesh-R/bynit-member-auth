package com.dosmartie.controller.auth;

import com.dosmartie.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dosmartie.helper.Constants.EMAIL;
import static com.dosmartie.helper.Urls.MEMBER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(value = MEMBER)
public class MemberController {
    @Autowired
    private MemberService memberService;


    @GetMapping(value = "/user")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(AUTHORIZATION) String token) {
        return memberService.getCustomerProfileInfo(token);
    }

    @GetMapping(value = "/merchant")
    public ResponseEntity<?> getMerchantDetails(@RequestHeader(AUTHORIZATION) String token) {
        return memberService.getMerchantProfileInfo(token);
    }

    @GetMapping(value = "/company")
    public ResponseEntity<?> getCompanyDetail(@RequestHeader(EMAIL) String email) {
        return memberService.getMerchantProfileInfo(email);
    }

}