package com.dosmartie;

import com.dosmartie.authconfig.JwtTokenUtil;
import com.dosmartie.entity.CustomerInfo;
import com.dosmartie.entity.MerchantInfo;
import com.dosmartie.helper.ResponseMessage;
import com.dosmartie.response.CustomerResponse;
import com.dosmartie.response.MerchantResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService{

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomerRepository customerRepository;
    private final MerchantRepository merchantRepository;
    private final ResponseMessage<Object> responseMessage;
    private final ObjectMapper mapper;

    @Autowired
    public MemberServiceImpl(JwtTokenUtil jwtTokenUtil, CustomerRepository customerRepository, MerchantRepository merchantRepository, ResponseMessage<Object> responseMessage, ObjectMapper mapper) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.customerRepository = customerRepository;
        this.merchantRepository = merchantRepository;
        this.responseMessage = responseMessage;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<?> getCustomerProfileInfo(String token) {
        try {
            return getCustomerByEmail(jwtTokenUtil.getUsernameFromToken(token))
                    .map(customerInfo -> ResponseEntity.ok(responseMessage.setSuccessResponse("Fetched user", mapper.convertValue(customerInfo, CustomerResponse.class))))
                    .orElseGet(() -> ResponseEntity.ok(responseMessage.setFailureResponse("User not found/Invalid token")));
        }
        catch (Exception exception) {
            return ResponseEntity.ok(responseMessage.setFailureResponse("Unable to fetch user", exception));
        }
    }
    @Override
    public ResponseEntity<?> getMerchantProfileInfo(String token) {
        try {
            return getMerchantByEmail(jwtTokenUtil.getUsernameFromToken(token))
                    .map(merchantInfo -> ResponseEntity.ok(responseMessage.setSuccessResponse("Fetched user", mapper.convertValue(merchantInfo, MerchantResponse.class))))
                    .orElseGet(() -> ResponseEntity.ok(responseMessage.setFailureResponse("User not found/Invalid token")));
        }
        catch (Exception exception) {
            return ResponseEntity.ok(responseMessage.setFailureResponse("Unable to fetch user", exception));
        }
    }

    @Override
    public ResponseEntity<?> companyProfile(String email) {
        return null;
    }


    // Database queries
    private synchronized Optional<CustomerInfo> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
    private synchronized Optional<MerchantInfo> getMerchantByEmail(String email) {
        return merchantRepository.findByEmail(email);
    }
}
