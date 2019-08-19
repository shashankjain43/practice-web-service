
package com.snapdeal.ums.admin.ext.bulkemail;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetResultsBounceRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1395136266470615652L;
	@Tag(3)
    private int start;
    @Tag(4)
    private int number;
    @Tag(5)
    private String city;

    public GetResultsBounceRequest() {
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

    public GetResultsBounceRequest(int start, int number, String city) {
        this.start = start;
        this.number = number;
        this.city = city;
    }

}
