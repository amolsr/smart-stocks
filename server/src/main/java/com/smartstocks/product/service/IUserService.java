package com.smartstocks.product.service;

import com.smartstocks.product.dto.StocksToBuy;
import com.smartstocks.product.models.StockTransactions;
import com.smartstocks.product.models.StocksBought;
import com.smartstocks.product.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

public interface IUserService {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
    void saveUser(User user);
    boolean isEmailTaken(String email);
    void updateUser(User user);
    User getUserByEmail(String email);
    double buyStock(String email, List<StocksBought> stocksToBuy, List<Double> currentPrice);
    double sellStock(String email, List<StocksToBuy> stocksToSell, List<Double> currentPrice);
    void addMoney(String email, double money);
    Set<StockTransactions> getNonCreditTransactions(String email);
    Set<StockTransactions> getCreditTransactions(String email);
}
