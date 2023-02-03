package com.auction.repository.auction;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.auction.model.category.Category;

import com.auction.model.auction.AuctionState;

public interface AuctionStateRepository  extends JpaRepository<AuctionState, Integer> {

    List<AuctionState> findByClientId(Integer clientId);

    List<AuctionState> findByCategoryId(int categoryId);

    @Query("select a from v_auction_state a where a.category = :category and (a.isFinished = :status1 or a.isFinished = :status2) and (upper(a.productName) like %:motCle% or upper(a.productDescription) like %:motCle%) and a.endDate between :minDate and :maxDate and a.amountMin between :minAmount and :maxAmount")
    List<AuctionState> search(Category category, String motCle, Date minDate, Date maxDate, Double minAmount,Double maxAmount, Integer status1, Integer status2);
}
