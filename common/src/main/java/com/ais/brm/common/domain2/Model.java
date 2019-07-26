package com.ais.brm.common.domain2;

/**
 * Created by zhaocw on 2016-7-18.
 * @author zhaocw
 */
public class Model {
    private long id;
    private boolean enabled;
    private boolean locked;
    private int modelTypeId;///todo delete this property
    private String name;
    private String description;
    private int riskObjectTypeId;
    private int riskCategoryId;
    private int collectIntervalId;
    private boolean isHourly;
    private int doDetailNow;

    public int getDoDetailNow() {
        return doDetailNow;
    }

    public void setDoDetailNow(int doDetailNow) {
        this.doDetailNow = doDetailNow;
    }

    public boolean isHourly() {
		return isHourly;
	}

	public void setHourly(boolean isHourly) {
		this.isHourly = isHourly;
	}

	public Model(long modelId) {
        this.id = modelId;
    }

    public Model() {}


    public int getRiskObjectTypeId() {
        return riskObjectTypeId;
    }

    public void setRiskObjectTypeId(int riskObjectTypeId) {
        this.riskObjectTypeId = riskObjectTypeId;
    }

    public int getRiskCategoryId() {
        return riskCategoryId;
    }

    public void setRiskCategoryId(int riskCategoryId) {
        this.riskCategoryId = riskCategoryId;
    }

    public int getCollectIntervalId() {
        return collectIntervalId;
    }

    public void setCollectIntervalId(int collectIntervalId) {
        this.collectIntervalId = collectIntervalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Model risk = (Model) o;

        return id == risk.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int getModelTypeId() {
        return modelTypeId;
    }

    public void setModelTypeId(int modelTypeId) {
        this.modelTypeId = modelTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", enabled=" + enabled +
                ", modelTypeId=" + modelTypeId +
                ", name='" + name +
                '}';
    }
}
