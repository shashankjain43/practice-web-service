INSERT INTO `email_template` (`name`,`subject_template`,`body_template`,`from`,`to`,`cc`,`bcc`,`reply_to`,`email_channel_id`,`email_type`,`enabled`,`created`) VALUES ('newMobileRegistration','Your account created on Snapdeal.com','<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"640\" align=\"center\" style=\"border:1px solid #;f5f5f5\">\r\n  <tr>\r\n    <td width=\"6\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/spacer.gif\" width=\"6\" height=\"1\" align=\"left\" /></td>\r\n    <td width=\"628\"><a href=\"http://www.snapdeal.com/deals-${city.pageUrl}?utm_source=DailyNewsletter&utm_medium=Email&utm_campaign=Delhi-NCR&utm_content=RegularNewsletterProduct&utm_term=Logo&email=%%EMAIL_ADDRESS%%\" target=\"_blank\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/SnapdealLogo.jpg\" alt=\" Snapdeal.com\" title=\" Snapdeal.com\" border=\"0\" style=\" color:#000; font: bold 18px Tahoma, Geneva, sans-serif; background:#cccccc;\" height=\"42\" width=\"151\" /></a></td>\r\n    <td width=\"6\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/spacer.gif\" width=\"6\" height=\"1\" align=\"left\" /></td>\r\n  </tr>\r\n  <tr>\r\n    <td></td>\r\n    <td><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-bottom:1px dotted #ababab;\">\r\n          <tr>\r\n            <td height=\"56\" width=\"190\" style=\"border-right:1px dotted #ababab;\"><span style=\"color:#919191; font-size:13px; font-family:Tahoma, Geneva, sans-serif;\">$dateutils.dateToString($newsletterDate, \'EEEE, MMM dd, yyyy\')</span></td>\r\n            <td style=\"width:104px; border-right:1px dotted #ababab; padding:0 3px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/bag_icon.gif\" align=\"left\" hspace=\"3\" vspace=\"6\" width=\"32\" height=\"22\" border=\"0\" /> <span style=\"font-size:13px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;\"><strong>CASH</strong></span> <br />\r\n              <span style=\"font-size:11px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;\">on Delivery</span></td>\r\n            <td style=\"width:149px; border-right:1px dotted #ababab; padding:0 6px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/free_shipping_icon.gif\" align=\"left\" vspace=\"6\" hspace=\"3\" width=\"32\" height=\"22\" border=\"0\" /> <span style=\"font-size:13px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;\"><strong>FREE Shipping</strong></span> <br />\r\n              <span style=\"font-size:11px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;\">Across India</span></td>\r\n            <td style=\"width:174px; padding:0 6px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/emi_icon.gif\" align=\"left\" hspace=\"3\" vspace=\"6\" width=\"24\" height=\"22\" border=\"0\" /> <span style=\"font-size:13px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;\"><strong>EMI Options <a href=\"http://www.snapdeal.com/info/faq/EMI\" target=\"_blank\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/info_icon.gif\" style=\"vertical-align:-2px;\" alt=\"\" border=\"0\" width=\"13\" height=\"12\" title=\"Read FAQ for EMI\" /></a></strong></span><br />\r\n            <span style=\"font-size:11px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;\">Available in 3 &amp; 6 months</span></td>\r\n          </tr>\r\n        </table></td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td height=\"3\"></td>\r\n    <td><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/spacer.gif\" height=\"3\" width=\"1\" align=\"left\" /></td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td></td>\r\n    <td><table width=\"628\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#f4f4f4\">\r\n  <tr>\r\n    <td width=\"150\" height=\"32\"><img src=\"http://i.sdlcdn.com/img/marketing-mailers/mailer/2012/newsletter_icon/whats_new.gif\" width=\"119\" height=\"31\" align=\"left\" /></td>\r\n    <td width=\"31\"><img src=\"http://i.sdlcdn.com/img/marketing-mailers/mailer/2012/newsletter_icon/mobile_website.gif\" width=\"28\" height=\"31\" border=\"0\" align=\"left\" /></td>\r\n    <td width=\"125\" style=\"border-right:1px solid #cccccc;\"><a href=\"http://m.snapdeal.com/?utm_source=DailyNewsletter&utm_medium=Email&utm_campaign=Delhi-NCR&utm_content=RegularNewsletterProduct&utm_term=WAP&email=%%EMAIL_ADDRESS%%\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Mobile Website</a></td>\r\n    <td width=\"41\"><img src=\"http://i.sdlcdn.com/img/marketing-mailers/mailer/2012/newsletter_icon/festive.gif\" width=\"28\" height=\"31\" border=\"0\" hspace=\"4\" align=\"right\" /></td>\r\n    <td width=\"126\" style=\"border-right:1px solid #cccccc;\"><a href=\"http://www.snapdeal.com/offers/new-launches?utm_source=DailyNewsletter&utm_medium=Email&utm_campaign=Delhi-NCR&utm_content=RegularNewsletterProduct&utm_term=NewLaunch&email=%%EMAIL_ADDRESS%%\" target=\"_blank\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">New Launches</a></td>\r\n    <td width=\"41\"><img src=\"http://i.sdlcdn.com/img/marketing-mailers/mailer/2012/newsletter_icon/brand_store.gif\" width=\"28\" height=\"31\" border=\"0\" align=\"right\" /></td>\r\n    <td width=\"126\" style=\"padding:0 6px;\"><a href=\"http://www.snapdeal.com/brands?utm_source=DailyNewsletter&utm_medium=Email&utm_campaign=Delhi-NCR&utm_content=RegularNewsletterProduct&utm_term=BrandStores&email=%%EMAIL_ADDRESS%%\" target=\"_blank\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Brand Stores</a></td>\r\n  </tr>\r\n</table></td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td height=\"3\"></td>\r\n    <td style=\"border-bottom:1px dotted #ababab;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/spacer.gif\" height=\"3\" width=\"1\" align=\"left\" /></td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td height=\"12\"></td>\r\n    <td><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/spacer.gif\" height=\"12\" width=\"1\" align=\"left\" /></td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td></td>\r\n    <td><span style=\"color:#5a5a5a; font:bold 14px/16px Arial, Helvetica, sans-serif ;\">Dear User,<br />\r\n      <br></span>\r\n    <span style=\"color:#5a5a5a; font:normal 12px/16px Arial, Helvetica, sans-serif ;\">Welcome to Snapdeal.com - India\'s largest online marketplace, featuring a wide assortment of products across categories like Gadgets, Fashion, Home, Sports, Books and services like Restaurants, Spas &amp; Entertainment. With over 18 million members, Snapdeal.com is the shopping destination for millions of internet users across the country.<br>\r\n<br>\r\nTo make your online shopping experience even better, here are the details of the amazing offers that you had chosen.<br>\r\n<br>\r\nHappy Shopping at Snapdeal!</span></td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td></td>\r\n    <td height=\"16\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/spacer.gif\" height=\"16\" width=\"1\" align=\"left\" /></td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td></td>\r\n    <td style=\"-moz-border-radius:4px 4px 0 0; -webkit-border-radius:4px 4px 0 0; border-radius:4px 4px 0 0; border-bottom:1px solid #ffffff; background:#e5e5e5; height:32px;\"><table width=\"618\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  <tr>\r\n  	<td width=\"8\"></td>\r\n    <td width=\"460\" style=\"font:bold 16px/30px Arial, \'Segoe UI\'; color:#000000;\">Selected Offer</td>\r\n    <td align=\"right\" width=\"150\"><a href=\"#\" style=\"color:#0266a9; font:normal 11px/18px Arial, sans-serif; text-align:right;\">How to use Promocode?</a></td>\r\n  </tr>\r\n</table>\r\n</td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td></td>\r\n    <td height=\"12\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/spacer.gif\" height=\"12\" width=\"1\" align=\"left\" /></td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td></td>\r\n    <td><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"628\">\r\n  <tr>\r\n    <td rowspan=\"4\" width=\"190\"><img src=\"images/img1.jpg\" width=\"173\" height=\"146\" hspace=\"8\" vspace=\"0\" align=\"left\" style=\"border:1px solid #e8e8e8;\" /></td>\r\n    <td height=\"30\"><span style=\"color:#218fd8; font:normal 15px/20px Arial, sans-serif;\">30% off on Dominos</span></td>\r\n  </tr>\r\n  <tr>\r\n    <td><span style=\"color:#5a5a5a; font:normal 12px/16px Arial, sans-serif;\">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim <br />\r\n      <br />\r\n      <strong>Valid till : 30th Oct, 2012</strong></span></td>\r\n  </tr>\r\n  <tr>\r\n    <td height=\"20\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border:1px dotted #ababab;\">\r\n  <tr><td width=\"6\"></td>\r\n    <td height=\"20\"><span style=\"color:#5a5a5a; font:bold 12px/18px Arial, sans-serif;\">Promocode : xxxxxxxxxx</span></td><td width=\"6\"></td>\r\n  </tr>\r\n</table></td>\r\n  </tr>\r\n  <tr>\r\n    <td><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  <tr><td width=\"6\"></td>\r\n    <td height=\"20\" bgcolor=\"#218fd8\"><a style=\"color:#ffffff; font:bold 12px/18px Arial, sans-serif; padding:5px;\">Avail Now &gt;&gt; </a></td>\r\n    <td width=\"6\"></td>\r\n  </tr>\r\n</table></td>\r\n  </tr>\r\n</table></td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td></td>\r\n    <td></td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td></td>\r\n    <td height=\"12\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/spacer.gif\" height=\"12\" width=\"1\" align=\"left\" /></td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td></td>\r\n    <td><table width=\"620\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr>\r\n    <td height=\"20\" style=\"font:bold 15px Tahoma, Geneva, sans-serif; color: #c8890f; \"><div style=\"margin:0 6px; padding:4px 8px; border-bottom:1px dotted #ababab; border-top:1px dotted #ababab;\">Browse Categories</div></td>\r\n  </tr>\r\n  <tr>\r\n    <td><table width=\"100%\" cellpadding=\"4\" cellspacing=\"0\" border=\"0\" bgcolor=\"#ffffff\">\r\n  <tr>\r\n    <td colspan=\"7\" height=\"1\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/spacer.gif\" height=\"1\" width=\"1\" /></td>\r\n  </tr>\r\n  <tr>\r\n    <td width=\"5\"></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/mob_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/mobiles?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=Mobiles&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Mobiles &amp; Accessories</a></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/wba_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/lifestyle?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=Lifestyle&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none;\">Watches, Bags &amp; Accessories</a></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/cp_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/computers?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=Computers&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Computer &amp; Peripherals</a></td>\r\n  </tr>\r\n  <tr>\r\n    <td width=\"10\"></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/wa_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/women-apparel?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=WomenApparel&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none;\">Women&rsquo;s Apparel</a></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/pbh_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/perfumes-beauty?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=PerfumesBeauty&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Perfumes, Beauty &amp; Hygiene</a></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/tavg_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/electronic?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=Electronic&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">TVs, Audio/Video &amp; Gaming</a></td>\r\n  </tr>\r\n  <tr>\r\n    <td width=\"10\"></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/ec_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/cameras?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=Cameras&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Cameras &amp; Accessories</a></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/mf_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/mens-footwear?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=MenFootwear&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Men&rsquo;s Footwear</a></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/jw_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/jewelry?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=Jewelry&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Jewelry</a></td>\r\n  </tr>\r\n  <tr>\r\n    <td width=\"10\"></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/hk_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/home-kitchen?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=HomeKitchen&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none;\">Home &amp; Kitchen</a></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/ma_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/men-apparel?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=MenApparel&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none;\">Men&rsquo;s Apparel</a></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/app_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/appliances?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=Appliances&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none;\">Appliances</a></td>\r\n  </tr>\r\n  <tr>\r\n    <td width=\"10\"></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/ikt_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/kids-toys?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=KidsToys&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Infant, Kids &amp; Toys</a></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/wf_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/womens-footwear?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=WomensFootwear&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Women&rsquo;s Footwear</a></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/bms_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/books-movies?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=BooksMovies&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Books, Movies &amp; Stationery</a></td>\r\n  </tr>\r\n  <tr>\r\n    <td width=\"10\"></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/sa_icon.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/sports-hobbies?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=SportsHobbies&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Sports, Fitness &amp; Music Instr</a></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i.sdlcdn.com/img/marketing-mailers/2012/topsellers_12sep/images/health.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/health?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=Health&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Health &amp; Nutrition</a></td>\r\n    <td style=\"width:23px;\"><img src=\"http://i.sdlcdn.com/img/marketing-mailers/2012/topsellers_12sep/images/automotive.gif\" width=\"23\" height=\"20\" border=\"0\" /></td>\r\n    <td><a href=\"http://www.snapdeal.com/products/automotive?utm_source=DailyNewsletter&utm_campaign=Exp23_121012&utm_content=Automotive&utm_term=Footer&email={emailid}\" style=\"color:#0097c7; font-family:Tahoma, Geneva, sans-serif; font-size:12px; text-decoration:none\">Automotive</a></td>\r\n  </tr>\r\n  <tr>\r\n    <td colspan=\"7\" width=\"10\" height=\"3\"><img src=\"http://i.sdlcdn.com/img/marketing-mailers/mailer/2012/18category_11oct/appliances/images/zero.gif\" height=\"3\" width=\"1\" /></td>\r\n  </tr>\r\n</table></td>\r\n  </tr>\r\n</table></td>\r\n    <td></td>\r\n  </tr>\r\n  <tr>\r\n    <td colspan=\"3\"><table align=\"center\" width=\"640\" cellpadding=\"0\" cellspacing=\"0\" style=\"background:#000000;\">\r\n  <tr>\r\n    <td width=\"340\" style=\"padding:11px;\"><span style=\"color:#d6d2d2; font: normal 10px Segoe UI, Tahoma;\"><a href=\"http://www.snapdeal.com/info/terms\" style=\"color:#d6d2d2; text-decoration:none\">Privacy Policy</a> | <a href=\"#\" style=\"color:#d6d2d2; text-decoration:none\">Contact Us</a> | <a href=\"#\" style=\"color:#d6d2d2; text-decoration:none\">Feedback</a></span></td>\r\n    <td width=\"100\"><span style=\"color:#d6d2d2; font: normal 10px Segoe UI, Tahoma \">Show us your love :</span></td>\r\n    <td width=\"30\"><a href=\"http://www.facebook.com/SnapDeal\" target=\"_blank\"><img src=\"http://i.sdlcdn.com/img/mail/snapdeal/dailynewsletter/fb.gif\"  border=\"0\" style=\"color:#000; background:#fff; font: bold 14px Segoe UI, Tahoma;\" height=\"20\" width=\"20\" alt=\" F\"  /></a></td>\r\n    <td width=\"30\"><a href=\"http://www.twitter.com/snapdeal\" target=\"_blank\"><img src=\"http://i.sdlcdn.com//img/mail/snapdeal/dailynewsletter/twitter.gif\"  border=\"0\" style=\"color:#000; background:#fff; font: bold 14px Segoe UI, Tahoma;\" height=\"20\" width=\"20\"  alt=\" T\" /></a></td>\r\n    <td width=\"30\"><a href=\"https://plus.google.com/100573109232340925417?prsrc=3\" target=\"_blank\"><img src=\"images/googleplus.gif\"  border=\"0\" style=\"color:#000; background:#fff; font: bold 14px Segoe UI, Tahoma;\" height=\"19\" width=\"18\" alt=\" F\"  /></a></td>\r\n    <td width=\"30\"><a href=\"http://pinterest.com/snapdeal/\" target=\"_blank\"><img src=\"images/pin.gif\"  border=\"0\" style=\"color:#000; background:#fff; font: bold 14px Segoe UI, Tahoma;\" height=\"19\" width=\"18\" alt=\" F\"  /></a></td>\r\n  </tr>\r\n</table></td>\r\n  </tr>\r\n</table>\r\n<table align=\"center\" width=\"620\">\r\n<tr><td style=\"font-family:Segoe UI, Arial, sans-serif; font-size: 11px; color: rgb(136, 136, 136); padding: 10px;\">\r\nSnapDeal has sent this e-mail to you because your subscription preferences indicate that you want to receive latest deals alerts in your city on this email ID. <br/>\r\nIf you prefer not to receive the daily email, click here to <a href=\"http://www.snapdeal.com/unsubscribe?val={val}&zone={zone}\" style=\"color: rgb(92, 121, 150); text-decoration: underline;\" target=\"blank\">unsubscribe</a> <br/>\r\nIf you prefer not to receive the daily SMS, click here to <a href=\"http://www.snapdeal.com/smsunsubscribe?val={val}&zone={zone}\" style=\"color: rgb(92, 121, 150); text-decoration: underline;\" target=\"blank\">unsubscribe</a> <br/>\r\nSnapDeal (Jasper Infotech Pvt. Ltd.) is located at 246, Okhla, Phase - III, 2nd floor, New Delhi - 110020</td>\r\n</tr>                                      \r\n</table>','Snapdeal<newsletter@snapdeals.co.in>','','','','noreply@snapdeal.in',4,'nul',1,'2013-11-12 19:07:30');

