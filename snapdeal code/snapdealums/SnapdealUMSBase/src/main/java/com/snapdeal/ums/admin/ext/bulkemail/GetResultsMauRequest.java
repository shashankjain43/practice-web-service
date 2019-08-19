
package com.snapdeal.ums.admin.ext.bulkemail;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetResultsMauRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7978090457182916127L;
	@Tag(3)
    private int start;
    @Tag(4)
    private int number;
    @Tag(5)
    private String city;

    public GetResultsMauRequest() {
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public GetResultsMauRequest(int start, int number, String city) {
        this.start = start;
        this.number = number;
        this.city = city;
    }

}
