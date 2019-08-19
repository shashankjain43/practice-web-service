
export class FAQ{
    static get getFaq() {
      return [{
                "topic": "MERCHANT ACCOUNT",
                "faqs": [{
                    "q": "What is FreeCharge merchant account?",
                    "a": "FreeCharge merchant account is a payments product which enables merchants to accept payments  from their customers who use FreeCharge account for online/offline transaction. We partner with acquiring banks to give you the ability to accept payments. Here are a few of the many benefits of partnering with FreeCharge:<ul><li>Access to millions of customers</li><li>Secure transactions</li><li>No hidden costs</li></ul>"
                }, {
                    "q": "Is the FreeCharge account safe to use?",
                    "a": "Yes, using FreeCharge account is absolutely safe. Not only is our mobile payments system licensed by RBI but we are PCI DSS (Payment Card Industry Data Security Standard) compliant as well."
                }, {
                    "q": "What security standard is used by FreeCharge?",
                    "a": "FreeCharge uses a 128 bit encryption secure socket layer (SSL) to encrypt customer data during a transaction."
                }, {
                    "q": "How do I create my merchant account?",
                    "a": "Creating a merchant account is easy. Simply write to us at merchantsupport@freecharge.com and we'll create your account right away!"
                }, {
                    "q": "Is there any fee for registration on FreeCharge?",
                    "a": "No, there is no registration fee for signing up on FreeCharge."
                }, {
                    "q": "How do I change my password?",
                    "a": "Go to the 'My Account' section in your dashboard and click on 'Change Password'"
                }, {
                    "q": "I have forgotten my password, how do I set a new one?",
                    "a": "Click on 'Forgot Password' and we'll send a One time password (OTP) on your registered email ID to help you set a new password. Go to the link and follow the mentioned steps."
                }, {
                    "q": "How do I change the email ID/contact number?",
                    "a": "You cannot change your login email ID. However, you can change your business email ID/contact number by writing to merchantsupport@freecharge.com."
                }, {
                    "q": "How can I add users to my account?",
                    "a": "To add users to your merchant account<br/>- Go to 'My Profile' section in your dashboard <br/>- Click on 'Manage Users' and subsequently on 'Add User'<br/>- Enter the new user's personal details and select the permissions you wish to grant him/her.<br/>That's it! You've successfully added a new user to your account."
                }, {
                    "q": "How can I edit my account details?",
                    "a": "Account details can be edited by the admin at our end. You just need write to us at merchantsupport@freecharge.com and we will take care of the rest."
                }, {
                    "q": "What are the KYC documents required?",
                    "a": "You can get in touch with us at merchantsupport@freecharge.com to know the exact KYC requirements."
                }, {
                    "q": "Can I change my bank details?",
                    "a": "To edit your bank details, you need to raise request with the admin by writing to merchantsupport@freecharge.com. Post bank verification, you'll be able to view the changes in your dashboard. Please note that while we verify your bank details, your daily settlement report will be put on hold. "
                }, {
                    "q": "Is there a mobile app for the FreeCharge merchant?",
                    "a": "No, there is no mobile app for the FreeCharge merchant as of now."
                }, {
                    "q": "What does 'Account on Hold' mean? Why has my account been put on hold?",
                    "a": "Account on hold means you will not be able to receive payments, settlement reports or access your dashboard during an indefinite period of time, till your case is assessed by our team. Usually, an account is put on hold either due to excessive refunds/complaints by customers."
                }, {
                    "q": "How long does an account stay on hold?",
                    "a": "It depends on how soon you submit the requisite information or documents."
                }, {
                    "q": "How do I unblock my account?",
                    "a": "Unblocking an account is not possible."
                }, {
                    "q": "How do I close my account?",
                    "a": "Your account will be closed once you've raised a closure request with us."
                }]
            }, {
                "topic": "TRANSACTION & SETTLEMENT",
                "faqs": [{
                    "q": "What happens if internet connectivity gets disrupted during payment transaction?",
                    "a": "In such as case, if the payment was authorised by the bank no communication regarding the same was received by FreeCharge, the customer's account will get debited by the bank. "
                }, {
                    "q": "I am getting transaction status as pending but the customer was charged. What's happening?",
                    "a": "This happens when the transaction response from the bank gets interrupted due to connection failure. Although such a case occurs rarely, you should drop an email with the payment details to us."
                }, {
                    "q": "Are saved card details of customers secure with FreeCharge account?",
                    "a": "We ensure the customer's card details are extremely safe and secure with a dedicated, PCI DSS compliant encrypted server. The card details can be edited/removed anytime by the customer. <br/>Once a customer has stored his/her card details, he/she has to enter only the CVV and 3-D secure password during the next transaction. This leads to faster transactions and increased purchases."
                }, {
                    "q": "Do I get any communication if a customer tries to make a payment on my website?",
                    "a": "Yes, you will receive a notification email for every successful and unsuccessful payment."
                }, {
                    "q": "Why was transaction declined by FreeCharge account?",
                    "a": "Unless specified, a transaction is usually declined by FreeCharge account due to security concerns."
                }]
            }, {
                "topic": "REFUND AND CHARGEBACKS",
                "faqs": [{
                    "q": "Is there a dispute resolution policy in place?",
                    "a": "No, there is no dispute resolution policy in place at present. But we are working on creating one to ensure speedy resolutions for bioth merchants and customers."
                }, {
                    "q": "How do I initiate a refund?",
                    "a": "To initiate a refund <br/>- Go to 'Payments' section in your dashboard <br/>- Search for the transaction to be refunded using either the transaction ID, customer ID or the product ID <br/>- Once you've selected the transaction, write the reason for the refund and the amount to be refunded <br/>Click on 'Refund' and that's how easy the process is."
                }, {
                    "q": "I have initiated a refund, how much time will it take for refund to reflect in customer's account?",
                    "a": "The refund will reflect instantly in the customer's account."
                }, {
                    "q": "In case of a cancelled or returned order, will I be charged any fee for the refund I initiate?",
                    "a": "No, there is no cancellation fee but bank charges won't be refunded."
                }, {
                    "q": "What is a chargeback?",
                    "a": "A charge back is a refund initiated by the customer when he/she contacts the issuing bank for reversal of a payment. In addition, a chargeback may also be initiated by the issuing bank becasue of a technical issue."
                }, {
                    "q": "What are the reasons for a chargeback?",
                    "a": "There could be many reasons for chargebacks but mostly due to: <br/>- When a customer claims that he/she hasn't received the product/service. <br/>- The product/service rendered by the merchant was not the same as listed on the website or was damaged when it reached the customer. <br/>- When a customer claims that the purchase of the product/service in question was not made by them."
                }, {
                    "q": "What happens in case of a chargeback requested because of a product/service not received?",
                    "a": "In such a case, we will request you and the customer to produce the relevant transaction documents and investigate the matter at our end. If the chargeback request is validated, the chargeback will be initiated or else we will raise a dispute with the bank. During this process we may put a hold on the amount in question."
                }, {
                    "q": "What happens in case of a chargeback requested because of an unauthorised payment?",
                    "a": "The process will be the same as mentioned in case of product/service not received."
                }]
            }, {
                "topic": "VOUCHERS AND REWARDS",
                "faqs": [{
                    "q": "What is a voucher?",
                    "a": "A voucher is an expirable amount you can give to customers from time to time and in turn increase customer loyalty."
                }, {
                    "q": "Is there a fee for giving a voucher?",
                    "a": "No, there is no fee for giving a voucher."
                }]
            },

            {
                "topic": "ACCOUNTING AND TAXES",
                "faqs": [{
                        "q": "What is settlement? How does the payment settlement process work?",
                        "a": "Settlement is the process of crediting your money into your verified bank account. After shipping the order to your customer, click on the release request button against the transaction and enter the dispatch details."
                    }, {
                        "q": "How much time does it take for settlement to get processed?",
                        "a": "Settlements for a merchant account are sent to the bank everyday and the funds are transferred into the merchant's bank account in 5 working days."
                    }, {
                        "q": "How do I know if I have received a settlement?",
                        "a": "We send daily settlement reports to merchants via emails. You can also view the report under the Settlement section in your merchant dashboard."
                    }, {
                        "q": "What is the frequency of receiving settlements?",
                        "a": "Settlements are done once everyday. All the payments to be settled are sent to the bank and thereafter deposited into your account in 5 working days."
                    }, {
                        "q": "Where can I generate my  transaction report?",
                        "a": "You can generate your transaction report in the 'Payments' section of your merchant dashboard."
                    }

                ]
            }, {
                "topic": "PRODUCT INTEGRATION",
                "faqs": [{
                    "q": "I have a live website, how much time will integration take?",
                    "a": "For live websites, integration should be a quick process. Simply follow the steps given in the integration document or connect your developer(s) with us in case of any confusion, and we'll ensure that you start receiving payments via FreeCharge in no time."
                }, {
                    "q": "Can I get technical help for integration? If yes, who do I contact?",
                    "a": "Yes, at FreeCharge we ensure that you get complete technical support during integration. Simply write to us at merchantsupport@freecharge.com and one of our engineers will connect with you."
                }, {
                    "q": "Can I integrate more than one domain to a single FreeCharge merchant account?",
                    "a": "No, you can integrate only one domain with a single FreeCharge account. If you wish to integrate a different domain, please register separately for it."
                }, {
                    "q": "I have done the integration but I'm still not able to see FreeCharge wallet as a payment option, why?",
                    "a": "This is because you haven't activated FreeCharge in the admin panel of your shopping cart yet. Simply enable it to see FreeCharge as a payment option."
                }]
            }
        ];

    }
}
