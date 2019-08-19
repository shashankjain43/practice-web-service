package com.snapdeal.ums.admin.sdwallet.sro;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.expression.Expression;

public class ActivityTypeSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8529141284238440566L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private String            name;
    @Tag(3)
    private String            code;
    @Tag(4)
    private String            sdCash;
    @Tag(5)
    private boolean           async;
    @Tag(6)
    private boolean           enabled;
    @Tag(7)
    private Integer           expiryDays;
    @Tag(8)
    private Expression        expression;
    @Tag(9)
    private Date              created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSdCash() {
        return sdCash;
    }

    public void setSdCash(String sdCash) {
        this.sdCash = sdCash;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getExpiryDays() {
        return expiryDays;
    }

    public void setExpiryDays(Integer expiryDays) {
        this.expiryDays = expiryDays;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public ActivityTypeSRO() {
        super();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
