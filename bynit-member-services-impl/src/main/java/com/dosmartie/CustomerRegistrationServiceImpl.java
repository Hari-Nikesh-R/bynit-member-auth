package com.dosmartie;

import com.dosmartie.request.UserRequest;
import com.dosmartie.entity.CustomerInfo;
import com.dosmartie.entity.MerchantInfo;
import com.dosmartie.helper.ResponseMessage;
import com.dosmartie.response.BaseResponse;
import com.dosmartie.response.CustomerResponse;
import com.dosmartie.response.MerchantResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {
    @Autowired
    private ResponseMessage<Object> responseMessage;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseEntity<BaseResponse<Object>> registerNewUser(UserRequest userRequest, String token) {
        try {
            switch (userRequest.getRole()) {
                case ADMIN, MERCHANT -> {
                    Optional<MerchantInfo> optionalCustomerInfo = merchantRepository.findByEmail(userRequest.getEmail());
                    return optionalCustomerInfo.map(data -> ResponseEntity.ok(responseMessage.setFailureResponse("User Already exist")))
                            .orElseGet(() -> {
                                userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
                                MerchantInfo customerInfo = mapper.convertValue(userRequest, MerchantInfo.class);
                                customerInfo.setAdmin(jwtTokenUtil.getRoleFromToken(token).equals("SUPER_ADMIN"));
                                return ResponseEntity.ok(responseMessage.setSuccessResponse("User Registered", mapper.convertValue(merchantRepository.save(customerInfo), MerchantResponse.class)));
                            });
                }
                case CUSTOMER -> {
                    Optional<CustomerInfo> optionalCustomerInfo = customerRepository.findByEmail(userRequest.getEmail());
                    return optionalCustomerInfo.map(data -> ResponseEntity.ok(responseMessage.setFailureResponse("User Already exist")))
                            .orElseGet(() -> {
                                userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
                                CustomerInfo customerInfo = mapper.convertValue(userRequest, CustomerInfo.class);
                                return ResponseEntity.ok(responseMessage.setSuccessResponse("User Registered", mapper.convertValue(customerRepository.save(customerInfo), CustomerResponse.class)));
                            });
                }
                default -> {
                    return ResponseEntity.ok(responseMessage.setFailureResponse("Role not specified"));
                }
            }
        } catch (Exception exception) {
            return ResponseEntity.ok(responseMessage.setFailureResponse("Something went wrong", exception));
        }
    }
}
