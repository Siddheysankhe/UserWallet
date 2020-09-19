package com.example.UserWallet.service;

import com.example.UserWallet.converter.TransactionConverter;
import com.example.UserWallet.dtos.MoneyTransferDto;
import com.example.UserWallet.dtos.TransactionDto;
import com.example.UserWallet.dtos.UserAccountDto;
import com.example.UserWallet.entity.Transaction;
import com.example.UserWallet.entity.UserAccount;
import com.example.UserWallet.enums.TransactionTypeEnum;
import com.example.UserWallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionConverter transactionConverter;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ConfigurationService configurationService;

    @Transactional(rollbackFor = RuntimeException.class)
    public Transaction createTransaction(Transaction transaction) throws Exception {

        BigDecimal balance = getBalanceForUser(transaction.getUserAccount().getId());

        if (transaction.getTransactionType() == TransactionTypeEnum.DEBIT) {
            if (balance.compareTo(transaction.getAmount()) < 0) {
                throw new Exception(String.format("Your wallet balance is %.2f hence cannot perform a DEBIT transaction of %.2f ",
                        balance.doubleValue(), transaction.getAmount().doubleValue()));
            }
        }
        transaction = transactionRepository.save(transaction);
        return transaction;
    }

    /**
     *
     * @param userId
     * @return
     * @throws Exception
     */
    private BigDecimal getBalanceForUser(Integer userId) throws Exception {
        List<Transaction> transactionsForUser = transactionRepository.getAllByUserAccount(userId);

        BigDecimal balance = BigDecimal.ZERO;
        for (Transaction transaction : transactionsForUser) {
            if (transaction.getTransactionType() == TransactionTypeEnum.CREDIT) {
                balance = balance.add(transaction.getAmount());
            } else if (transaction.getTransactionType() == TransactionTypeEnum.DEBIT) {
                balance = balance.subtract(transaction.getAmount());
            }
        }

        return balance;
    }

    /**
     *
     * @param userAccountId
     * @param transactionDto
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public TransactionDto addMoney(Integer userAccountId, TransactionDto transactionDto) throws Exception {
        UserAccountDto userAccount = userAccountService.getUser(userAccountId);
        transactionDto.setUserAccountId(userAccount.getId());

        if (transactionDto.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("Cannot Add Negative amount in wallet");
        }

        Transaction transaction = transactionConverter.convertModelToEntity(transactionDto);
        transaction = createTransaction(transaction);

        return transactionConverter.convertEntityToModel(transaction);
    }

    /**
     *
     * @param moneyTransferDto
     * @param toUserAccountId
     * @param fromUserAccountId
     * @return
     * @throws Exception
     */
    public List<TransactionDto> transfer(MoneyTransferDto moneyTransferDto, Integer toUserAccountId,
                                         Integer fromUserAccountId) throws Exception {
        // get transaction dto
        TransactionDto transactionDto = moneyTransferDto.getTransactionDto();

        UserAccountDto fromUserAccount = userAccountService.getUser(fromUserAccountId);
        UserAccountDto toUserAccount = userAccountService.getUser(toUserAccountId);

        if (transactionDto.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new Exception("Cannot Send Negative amount in wallet");
        }

        //updating transaction reference
        String referenceId = getTransactionReference();
        transactionDto.setTransactionReference(referenceId);

        //get wallet Manager User Account
        Integer walletMangerUserAccountId = configurationService.getWalletManagerUserId();
        UserAccountDto walletMangerUserAccount = userAccountService.getUser(walletMangerUserAccountId);

        //create CREDIT commissionTransaction
        Transaction commissionTransaction = getCommissionTransaction(transactionDto, referenceId, walletMangerUserAccount);

        //create CREDIT chargesTransaction
        Transaction chargesTransaction = getChargesTransaction(transactionDto, referenceId, walletMangerUserAccount);

        //compute amount to deduct
        BigDecimal amountToDeduct = commissionTransaction.getAmount().add(chargesTransaction.getAmount());


        List<TransactionDto> transactionDtoList = new ArrayList<>();

        //creating DEBIT transaction
        transactionDto.setUserAccountId(fromUserAccount.getId());
        transactionDto.setTransactionTypeEnum(TransactionTypeEnum.DEBIT);
        Transaction fromUserTransaction = transactionConverter.convertModelToEntity(transactionDto);
        fromUserTransaction = createTransaction(fromUserTransaction);
        transactionDtoList.add(transactionConverter.convertEntityToModel(fromUserTransaction));

        //subtracting amount with charges
        BigDecimal amount = transactionDto.getAmount().subtract(amountToDeduct);
        transactionDto.setAmount(amount);

        //creating CREDIT transaction
        transactionDto.setUserAccountId(toUserAccount.getId());
        transactionDto.setTransactionTypeEnum(TransactionTypeEnum.CREDIT);
        Transaction destinationUserTransaction = transactionConverter.convertModelToEntity(transactionDto);
        destinationUserTransaction = createTransaction(destinationUserTransaction);
        transactionDtoList.add(transactionConverter.convertEntityToModel(destinationUserTransaction));

        return transactionDtoList;
    }

    /**
     *
     * @param referenceId
     * @return
     * @throws Exception
     */
    public List<TransactionDto> getStatusOfTransaction(String referenceId) throws Exception {
        List<Transaction> transactions = transactionRepository.getAllByTransactionReference(referenceId);

        if (CollectionUtils.isEmpty(transactions)) {
            throw new Exception("No Transaction found with reference Id :" + referenceId);
        }

        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionDto transactionDto = transactionConverter.convertEntityToModel(transaction);
            transactionDtoList.add(transactionDto);
        }
        return transactionDtoList;
    }

    /**
     *
     * @param transactionDto
     * @param referenceId
     * @param walletMangerUserAccount
     * @return
     */
    private Transaction getCommissionTransaction(TransactionDto transactionDto, String referenceId,
                                                 UserAccountDto walletMangerUserAccount) throws Exception {
        BigDecimal transactionAmount = transactionDto.getAmount();
        BigDecimal commissionPercent = configurationService.getCommissionPercentage();

        BigDecimal commissionAmount = BigDecimal.ONE;
        commissionAmount = commissionAmount.multiply(transactionAmount).multiply(commissionPercent).divide(new BigDecimal(100));

        UserAccount userAccount = new UserAccount();
        userAccount.setId(walletMangerUserAccount.getId());

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionTypeEnum.CREDIT);
        transaction.setDetails(String.format("CREDIT entry for Commission with reference Id: %s", referenceId));
        transaction.setAmount(commissionAmount);
        transaction.setTransactionDate(new Date());
        transaction.setUserAccount(userAccount);
        transaction.setTransactionReference(referenceId);

        transaction = createTransaction(transaction);

        return transaction;
    }

    /**
     *
     * @param transactionDto
     * @param referenceId
     * @param walletMangerUserAccount
     * @return
     */
    private Transaction getChargesTransaction(TransactionDto transactionDto, String referenceId,
                                              UserAccountDto walletMangerUserAccount) throws Exception {
        BigDecimal transactionAmount = transactionDto.getAmount();
        BigDecimal chargesPercent = configurationService.getChargesPercentage();

        BigDecimal chargesAmount = BigDecimal.ONE;
        chargesAmount = chargesAmount.multiply(transactionAmount).multiply(chargesPercent).divide(new BigDecimal(100));

        UserAccount userAccount = new UserAccount();
        userAccount.setId(walletMangerUserAccount.getId());

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionTypeEnum.CREDIT);
        transaction.setDetails(String.format("CREDIT entry for Charges with reference Id: %s", referenceId));
        transaction.setAmount(chargesAmount);
        transaction.setTransactionDate(new Date());
        transaction.setUserAccount(userAccount);
        transaction.setTransactionReference(referenceId);

        transaction = createTransaction(transaction);

        return transaction;
    }

    /**
     *
     * @return
     */
    private String getTransactionReference() {
        return UUID.randomUUID().toString();
    }
}
