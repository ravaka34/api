package com.auction.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.auction.service.AuctionService;
import java.util.List;
import com.auction.model.auction.Auction;

@Component
public class ThreadNotif implements CommandLineRunner{
    @Autowired
    AuctionService auctionService;

    @Autowired 
    NotificationSender sender;

 
    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        while(true) {
            pushNotifFor(auctionService.findFinishedAndNotifNotSend());
            Thread.sleep(5000);
        }
    }

    private void pushNotifFor(List<Auction> auctions){
        for(Auction auction : auctions) {
            sender.push(auction);
            auctionService.updateSend(auction);
        }
    }

}
