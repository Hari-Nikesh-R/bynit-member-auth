package com.dosmartie;

import com.dosmartie.entity.MerchantInfo;
import com.dosmartie.helper.ResponseMessage;
import com.dosmartie.response.BaseResponse;
import com.dosmartie.response.MerchantResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private ResponseMessage<Object> responseMessage;
    @Autowired
    private ResponseMessage<List<MerchantResponse>> listResponseMessage;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public ResponseEntity<BaseResponse<Object>> verifyMerchant(String email) {
        try {
            return getMerchantByEmail(email).map(merchant -> {
                merchant.setVerified(true);
                merchantRepository.save(merchant);
                return ResponseEntity.ok(responseMessage.setSuccessResponse("Merchant verified", null));
            }).orElseGet(() -> ResponseEntity.ok(responseMessage.setFailureResponse("No Merchant found")));
        }
        catch (Exception exception) {
            return ResponseEntity.ok(responseMessage.setSuccessResponse("Merchant not verified", exception));
        }
    }

    @Override
    public ResponseEntity<BaseResponse<List<MerchantResponse>>> getNonVerifyMerchant() {
        try {
            List<MerchantInfo> merchantInfo = getNonVerifiedMerchantByEmail();
            return ResponseEntity.ok(Objects.nonNull(merchantInfo) ? listResponseMessage.setSuccessResponse("Fetched result", objectMapper.convertValue(merchantInfo, new TypeReference<>() {
            })) : listResponseMessage.setFailureResponse("There is no unverfied merchant"));
        }
        catch (Exception exception)
        {
            return ResponseEntity.ok(listResponseMessage.setFailureResponse("Unable To fetch result", exception));
        }
    }

    private synchronized List<MerchantInfo> getNonVerifiedMerchantByEmail() {
        return merchantRepository.findByNonVerified();
    }

    private synchronized Optional<MerchantInfo> getMerchantByEmail(String email) {
        return merchantRepository.findByEmail(email);
    }
}

