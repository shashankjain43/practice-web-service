package com.snapdeal.payments.view.merchant.commons.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.payments.view.commons.response.AbstractResponse;


@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class GetTotalUnsettledAmountRespnse extends AbstractResponse{
	
	private static final long serialVersionUID = 1758036917210099698L;
	
	private BigDecimal totalUnsettledAmount;

}
