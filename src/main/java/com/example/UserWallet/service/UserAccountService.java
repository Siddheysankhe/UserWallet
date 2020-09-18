package com.example.UserWallet.service;

import com.example.UserWallet.converter.UserAccountConverter;
import com.example.UserWallet.dtos.UserAccountDto;
import com.example.UserWallet.entity.UserAccount;
import com.example.UserWallet.exceptions.UserNotFoundException;
import com.example.UserWallet.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountConverter userAccountConverter;

    /**
     *
     * @param id
     * @return
     */
    public UserAccountDto getUser(Integer id) throws UserNotFoundException {
        Optional<UserAccount> userAccount = userAccountRepository.findById(id);
        UserAccountDto userAccountDto;
        if (userAccount.isPresent()) {
            userAccountDto = userAccountConverter.convertEntityToModel(userAccount.get());
        } else {
            throw new UserNotFoundException(String.format("No User Found for given id %d", id));
        }
        return userAccountDto;
    }

    /**
     *
     * @param userAccountDto
     * @return
     * @throws Exception
     */
    public UserAccountDto createUser(UserAccountDto userAccountDto) throws Exception {
        if (userAccountDto.getUserName() != null) {
            UserAccount userAccount = new UserAccount();
            userAccount.setUserName(userAccountDto.getUserName());
            userAccount.setEmail(userAccountDto.getEmail());
            userAccount.setCreatedAt(new Date());
            userAccount.setUpdatedAt(new Date());
            userAccount.setDeleted(0);
            userAccount = userAccountRepository.save(userAccount);

            return userAccountConverter.convertEntityToModel(userAccount);
        }
        throw new Exception("User Name is mandatory");
    }

    /**
     *
     * @param userAccountDto
     * @return
     * @throws Exception
     */
    public UserAccountDto updateUser(UserAccountDto userAccountDto) throws Exception {
        if (userAccountDto.getId() == null) {
            throw new Exception("User id cannot be null");
        }

        UserAccountDto userAccount = getUser(userAccountDto.getId());
        if (userAccountDto.getUserName() == null) {
            throw new Exception("User Name is mandatory");
        }
        userAccount.setUserName(userAccountDto.getUserName());
        userAccount.setEmail(userAccountDto.getEmail());

        UserAccount updatedUser = userAccountConverter.convertModelToEntity(userAccount);
        updatedUser = userAccountRepository.save(updatedUser);

        return userAccountConverter.convertEntityToModel(updatedUser);
    }
}
