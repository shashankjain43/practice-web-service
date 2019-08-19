package com.snapdeal.ums.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "task_parameter")
public class TaskParameter {

    private Integer id;
    private UmsTask umsTask;
    private String  name;
    private String  value;

    
    public TaskParameter() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TaskParameter(UmsTask task, String name) {
        this.umsTask = task;
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    public UmsTask getUmsTask() {
        return umsTask;
    }

    public void setUmsTask(UmsTask umsTask) {
        this.umsTask = umsTask;
    }

    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "value", length = 500)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
