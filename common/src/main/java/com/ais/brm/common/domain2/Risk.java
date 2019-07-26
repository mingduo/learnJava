package com.ais.brm.common.domain2;

/**
 * 风险.
 * Created by zhaocw on 2016-7-18.
 * @author zhaocw
 */
public class Risk {
    private long id;
    private boolean enabled;
    private boolean locked;
    private boolean hidden;
    private boolean template;
    private long templateId;
    private long modelId;
    private int riskType;
    private String riskCode;
    private String name;
    private String computerFrequence;
    private String description;
    private int updateType;
    private int detailType;//明细类型，积分还是台账.
    
    private int riskObjectTypeId;
    private int riskCategoryId;
    private boolean config;
    
    public Risk(long id, long modelId) {
        this.id = id;
        this.modelId = modelId;
    }

    public Risk() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Risk risk = (Risk) o;

        return id == risk.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public long getId() {
        return id;
    }

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
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

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    public int getRiskType() {
        return riskType;
    }

    public void setRiskType(int riskType) {
        this.riskType = riskType;
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

    public int getUpdateType() {
        return updateType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    public int getDetailType() {
        return detailType;
    }

    public void setDetailType(int detailType) {
        this.detailType = detailType;
    }

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getComputerFrequence() {
		return computerFrequence;
	}

	public void setComputerFrequence(String computerFrequence) {
		this.computerFrequence = computerFrequence;
	}

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

	public boolean isConfig() {
		return config;
	}

	public void setConfig(boolean config) {
		this.config = config;
	}    
}
