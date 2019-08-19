package com.snapdeal.payments.view.sqs;

import java.util.Map;

import lombok.Data;

/**
 * All Queue Users need to Extend this Class for building any Notification Message
 * @author shagun
 *
 */
@Data
public class PaymentsMessage {
   private String messageId;
   private String messageBody;
   private Map<String,String> headers;
   private String receiptHandle;
}
