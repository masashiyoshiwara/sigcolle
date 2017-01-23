package net.unit8.sigcolle.form;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;

/**
 * @author kawasima
 */
@Data
public class CampaignForm extends FormBase {
    @DecimalMin("1")
    @DecimalMax("9999")
    private String campaignId;

    private String title;

    // Markdown description
    private String statement;

    private String goal;

    private String createdBy;


    public Long getCampaignIdLong() {
        return Long.parseLong(campaignId);
    }
    public String getTitle() {
        return title;
    }
    public Long getGoalLong() {
        return Long.parseLong(goal);
    }
    public Long getCreatedByLong() {
        return Long.parseLong(createdBy);
    }
}
