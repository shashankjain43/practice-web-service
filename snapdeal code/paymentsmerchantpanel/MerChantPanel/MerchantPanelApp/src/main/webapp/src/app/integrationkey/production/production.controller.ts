import {GlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {IGlobalOmnitureService} from "../../shared/services/globalomniture.service";
import {APP_MESSAGE_CONSTANT} from "../../shared/constants/message.constant";
import {IProductionService} from "./production.interface";
import {ProductionService} from "./production.service";


"use strict";

/* @ngInject */
export class ProductionController {


  private
  GlobalOmnitureService:IGlobalOmnitureService;
  ProductionService:IProductionService;
  public APP_MESSAGES;
  public errorMessage;
  private productionData:any;
  public prodData:any;
  public productData:any;
  constructor(GlobalOmnitureService:IGlobalOmnitureService,
  APP_MESSAGE:APP_MESSAGE_CONSTANT,
  ProductionService: IProductionService,
  productionData:any)
  {

    this.GlobalOmnitureService = GlobalOmnitureService;
    this.APP_MESSAGES = APP_MESSAGE_CONSTANT.getMessageConstants;

    var vm = this;
    vm.errorMessage=vm.APP_MESSAGES.EMPTY_INTEGRATION_KEY+'production';
        function init()
        {
            var prodData = productionData.data;
            vm.productData = prodData;
            if(prodData.data !== null) {
                var url = prodData.data.url;
                var key = prodData.data.key;
            }
            if(prodData.error !== null)
                vm.errorMessage = prodData.error.errorMessage;
            GlobalOmnitureService.production(url, key, prodData.error);
        }
        init();
  }
}
