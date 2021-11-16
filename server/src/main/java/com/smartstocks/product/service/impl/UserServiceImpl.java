package com.smartstocks.product.service.impl;

import com.smartstocks.product.dto.StocksBoughtDto;
import com.smartstocks.product.dto.StocksToBuy;
import com.smartstocks.product.exception.InSufficientBalanceException;
import com.smartstocks.product.exception.InvalidTransactionException;
import com.smartstocks.product.exception.InvalidUnitException;
import com.smartstocks.product.models.*;
import com.smartstocks.product.repository.StockTransactionsRepository;
import com.smartstocks.product.repository.StocksBoughtRepository;
import com.smartstocks.product.repository.UserRepository;
import com.smartstocks.product.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService, IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StocksBoughtRepository stocksBoughtRepository;

    @Autowired
    private StockTransactionsRepository stockTransactionsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), new ArrayList<>());
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public boolean isEmailTaken(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public void updateUser(User user) {
         User persistUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + user.getEmail()));
         if(user.getDob() != null) persistUser.setDob(user.getDob());
         if(user.getFirstName() != null && !user.getFirstName().isEmpty()) persistUser.setFirstName(user.getFirstName());
         if(user.getLastName() != null && !user.getLastName().isEmpty()) persistUser.setLastName(user.getLastName());
         if(user.getPhoneNo() != null && !user.getPhoneNo().isEmpty())  persistUser.setPhoneNo(user.getPhoneNo());
         if(user.getGender() != null) persistUser.setGender(user.getGender());
         userRepository.save(persistUser);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
    }

    @Override
    @Transactional
    public double buyStock(String email, List<StocksBought> stocksToBuy, List<Double> currentPrice) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
        double totalCharges = 0;
        for(int i=0; i<stocksToBuy.size(); i++) {
            totalCharges += (stocksToBuy.get(i).getUnits() * currentPrice.get(i));
        }
        if(totalCharges > user.getWallet()) {
            throw new InSufficientBalanceException("You do not have Sufficient balance to process this transaction");
        }
        user.setWallet(user.getWallet()-totalCharges);
        for(int i=0; i<stocksToBuy.size(); i++) {
            Optional<StocksBought> isExistingStock = this.stocksBoughtRepository.findBySymbol(stocksToBuy.get(i).getSymbol());
            if(isExistingStock.isPresent()) {
                StocksBought present = isExistingStock.get();
                present.setAvgPrice((present.getUnits() * present.getAvgPrice() + currentPrice.get(i) * stocksToBuy.get(i).getUnits())
                        /(present.getUnits() + stocksToBuy.get(i).getUnits()));
                present.setUnits(present.getUnits() + stocksToBuy.get(i).getUnits());
                this.stocksBoughtRepository.save(present);
            }
            else {
                stocksToBuy.get(i).setAvgPrice(currentPrice.get(i));
                this.stocksBoughtRepository.save(stocksToBuy.get(i));
                user.getBought().add(stocksToBuy.get(i));
            }
            StockTransactions newTransaction = new StockTransactions();
            newTransaction.setUnits(stocksToBuy.get(i).getUnits());
            newTransaction.setSymbol(stocksToBuy.get(i).getSymbol());
            newTransaction.setPrice(currentPrice.get(i));
            newTransaction.setType(TransactionType.BUY);
            this.stockTransactionsRepository.save(newTransaction);
            user.getTransactions().add(newTransaction);
        }
        this.userRepository.save(user);
        return user.getWallet();
    }

    @Override
    @Transactional
    public double sellStock(String email, List<StocksToBuy> stocksToSell, List<Double> currentPrice) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
        for(int i=0; i<stocksToSell.size(); i++) {
            Optional<StocksBought> isExistingStock = this.stocksBoughtRepository.findBySymbol(stocksToSell.get(i).getSymbol());
            if(isExistingStock.isPresent()) {
                StocksBought present = isExistingStock.get();
                if(present.getUnits() >= stocksToSell.get(i).getUnits()) {
                    user.setWallet(user.getWallet() + currentPrice.get(i) * stocksToSell.get(i).getUnits());
                    present.setUnits(present.getUnits()-stocksToSell.get(i).getUnits());
                    if(present.getUnits() == 0) {
                        user.getBought().remove(present);
                        this.stocksBoughtRepository.delete(present);
                    }
                    else {
                        this.stocksBoughtRepository.save(present);
                    }
                    StockTransactions newTransaction = new StockTransactions();
                    newTransaction.setUnits(stocksToSell.get(i).getUnits());
                    newTransaction.setSymbol(stocksToSell.get(i).getSymbol());
                    newTransaction.setPrice(currentPrice.get(i));
                    newTransaction.setProfit(currentPrice.get(i) * stocksToSell.get(i).getUnits() - stocksToSell.get(i).getUnits() * present.getAvgPrice());
                    newTransaction.setType(TransactionType.SELL);
                    this.stockTransactionsRepository.save(newTransaction);
                    user.getTransactions().add(newTransaction);
                    user.setProfit(user.getProfit() + newTransaction.getProfit());
                }
                else {
                    throw new InvalidUnitException("You cannot sell more than " + present.getUnits() + " of " +
                            MockStocks.mockStocksMap.get(present.getSymbol()));
                }
            }
            else {
                throw new InvalidTransactionException("Cannot sell the stock you haven't bought");
            }
        }
        this.userRepository.save(user);
        return user.getWallet();
    }

    @Override
    public void addMoney(String email, double money) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
        user.setWallet(user.getWallet()+money);
        StockTransactions newTransaction = new StockTransactions();
        newTransaction.setPrice(money);
        newTransaction.setType(TransactionType.CREDIT);
        this.stockTransactionsRepository.save(newTransaction);
        user.getTransactions().add(newTransaction);
        this.userRepository.save(user);
    }

    @Override
    public Set<StockTransactions> getNonCreditTransactions(String email) {
        return this.userRepository.nonCreditTransactions(email);
    }

    @Override
    public Set<StockTransactions> getCreditTransactions(String email) {
        return this.userRepository.creditTransactions(email);
    }

}
