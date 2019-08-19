package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.admin.sdwallet.sro.SDWalletInfoSRO;

public class GetSDWalletByUserIdResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -1902579521726046635L;
    @Tag(5)
    private SDWalletInfoSRO   sdWalletInfoSRO;

    public GetSDWalletByUserIdResponse() {
    }

    public SDWalletInfoSRO getSdWalletInfoSRO() {
        return sdWalletInfoSRO;
    }

    public void setSdWalletInfoSRO(SDWalletInfoSRO sdWalletInfoSRO) {
        this.sdWalletInfoSRO = sdWalletInfoSRO;
    }

    public GetSDWalletByUserIdResponse(SDWalletInfoSRO sdWalletInfoSRO) {
        super();
        this.sdWalletInfoSRO = sdWalletInfoSRO;
    }

}
