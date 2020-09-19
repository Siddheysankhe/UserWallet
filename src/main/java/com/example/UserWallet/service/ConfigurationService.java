package com.example.UserWallet.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    @Value("transaction.transfer.commission.percent")
    private Double commissionPercentage;

    @Value("transaction.transfer.charges.percent")
    private Double chargesPercentage;

    public Double getCommissionPercentage() {
        return commissionPercentage;
    }

    public Double getChargesPercentage() {
        return chargesPercentage;
    }
}
