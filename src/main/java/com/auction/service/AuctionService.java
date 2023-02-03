package com.auction.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auction.exception.BetOnSelfException;
import com.auction.exception.WrongValueException;
import com.auction.model.ProductPicture;
import com.auction.model.auction.Auction;
import com.auction.model.auction.AuctionBet;
import com.auction.model.auction.AuctionSearch;
import com.auction.model.balance.BalanceProfil;
import com.auction.model.category.Category;
import com.auction.repository.auction.AuctionBetRepository;
import com.auction.repository.auction.AuctionRepository;
import com.auction.repository.category.CategoryRepository;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private AuctionBetRepository auctionBetRepo;

    @Autowired
    private CategoryRepository categoryRepository;



    public List<Auction> search(AuctionSearch search) {
        String motCle = (search.getMotCle() == null) ? "" : search.getMotCle();
        String str = "2017-12-03";
        Date date = Date.valueOf(str);
        System.out.println("Date Value: " + date);
        Date minDate = (search.getMinDate() == null) ? Date.valueOf("2000-01-01") : search.getMinDate();
        Date maxDate = (search.getMaxDate() == null) ? Date.valueOf("2030-01-01") : search.getMaxDate();
        Double minAmount = (search.getMinAmount() == null) ? 0 : search.getMinAmount();
        Double maxAmount = (search.getMaxAmount() == null) ? Double.MAX_VALUE : search.getMaxAmount();
        return auctionRepository.search(motCle, minDate, maxDate, minAmount, maxAmount);
    }

    public List<Auction> findByIdClient(int clientId) {
        return auctionRepository.findByClientId(clientId);
    }

    @Transactional(rollbackOn = Exception.class)
    public AuctionBet betOn(AuctionBet auctionBet) throws BetOnSelfException, WrongValueException {
        Auction auction = auctionRepository.findById(auctionBet.getAuctionId()).get();
        BalanceProfil bal = balanceService.getBalance(auctionBet.getClient().getId());
        if(bal.getAmount() <= 0 || bal.getAmount() < auctionBet.getAmount() ){
            throw new WrongValueException("You don t have money");
        }
        if(auction.getClient().getId() == auctionBet.getClient().getId()) {
            throw new WrongValueException("Can not bet on your own auction");
        }
        if (auction.getAmountMin() > auctionBet.getAmount()) {
            throw new WrongValueException("Amount is too low");
        }
        AuctionBet lastAuctionBet = auction.retrieveLastAuctionBet();
        if (lastAuctionBet != null && lastAuctionBet.getAmount() > auctionBet.getAmount()) {
            throw new WrongValueException("Amount is too low");
        }
        if (lastAuctionBet != null) {
            balanceService.creditAccount(lastAuctionBet.getClient().getId(), lastAuctionBet.getAmount());
            // debloquer
            balanceService.saveTransaction(lastAuctionBet.getClient().getId(), lastAuctionBet.getAmount(),
                   auctionBet.getDateBet(), 20, auctionBet.getAuctionId());
        }
    
        balanceService.debitAccount(auctionBet.getClient().getId(), auctionBet.getAmount());
        // bloquer
        Date now =  Date.valueOf(LocalDate.now());
        auctionBet.setDateBet(now);
        balanceService.saveTransaction(auctionBet.getClient().getId(), auctionBet.getAmount(),
        now , 10, auctionBet.getAuctionId());
        auctionBetRepo.save(auctionBet);
        return auctionBet;
    }
    
    public List<Auction> findCurrentAuctions() {
        return auctionRepository.findCurrentAuctions();
    }

    public Auction findById(int id) {
        return auctionRepository.findById(id).get();
    }

    public boolean save(Auction auction) throws WrongValueException {
        auction.setDepositoryDate(new Timestamp(System.currentTimeMillis()));
        auction.setEndDate(new Timestamp(auction.getDepositoryDate().getTime() + auction.getDuration().longValue()));
        Category category = categoryRepository.findById(auction.getCategory().getId()).get();
        if(auction.getDuration() < category.getMinDuration() || auction.getDuration() > category.getMaxDuration()) {
            throw new WrongValueException("Duration is not in the range");
        }
        if(auction.getProductPictureList() == null || auction.getProductPictureList().isEmpty()){
            throw new WrongValueException("Product picture is mandatory");
        }
        auction.setComission(category.getComission());
        for(ProductPicture picture : auction.getProductPictureList()) {
            picture.setAuction(auction);
        }
        auction.setIsNotifSend(false);
        auctionRepository.save(auction);
       
        return true;
    }

    public List<Auction> findByCategoryId(int categoryId) {
        return auctionRepository.findByCategoryId(categoryId);
    }

    public List<Auction> findFinishedAndNotifNotSend(){
        return auctionRepository.findFinishedAndNotifNotSend();
    }

    public boolean updateSend(Auction auction){
        auction.setIsNotifSend(true);
        auctionRepository.save(auction);
        return true;
    }
}
