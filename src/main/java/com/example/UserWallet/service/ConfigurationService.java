package com.example.UserWallet.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConfigurationService {

    @Value("${transaction.transfer.commission.percent}")
    private BigDecimal commissionPercentage;

    @Value("${transaction.transfer.charges.percent}")
    private BigDecimal chargesPercentage;

    @Value("${wallet.manager.user.account.id}")
    private Integer walletManagerUserId;

    public BigDecimal getCommissionPercentage() {
        return commissionPercentage;
    }

    public BigDecimal getChargesPercentage() {
        return chargesPercentage;
    }

    public Integer getWalletManagerUserId() {
        return walletManagerUserId;
    }
}
