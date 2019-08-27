package cn.sgf.asset.dto;

import lombok.Data;

@Data
public class AssetStatisticsDTO {
	private String type;
    private Long freeNum;
    private Long usedBorrowNum;
    private Long usedReceiveNum;
    private Long maintainNum;
    private Long scrappedNum;
    
    private Double totalPrice;
    
	public AssetStatisticsDTO(String type, Long freeNum, Long usedBorrowNum, Long usedReceiveNum,
			Long maintainNum, Long scrappedNum,Double totalPrice) {
		super();
		this.type = type;
		this.freeNum = freeNum;
		this.usedBorrowNum = usedBorrowNum;
		this.usedReceiveNum = usedReceiveNum;
		this.maintainNum = maintainNum;
		this.scrappedNum = scrappedNum;
		this.totalPrice=totalPrice;
	}
    
    
}
