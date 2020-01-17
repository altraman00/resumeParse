package com.mdl.zhaopin.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResumeParseWorkExpDTO implements Serializable {

    private String year;
    private String length;
    private String company;
    private String position;
    private String salary;
    private String industry;
    private String companyAsset;
    private List<String> desc;

    public void processTime() {
        if (this.year != null && this.year.contains(":00:00")) {
            String str1 = this.year.substring(0, this.year.indexOf(":00:00") - 3);
            this.year = this.year.substring(this.year.indexOf(":00:00") + 6);
            String str2 = "";
            if (this.year.contains(":00:00")) {
                str2 = this.year.substring(0, this.year.lastIndexOf(":00:00") - 3);
            }
            this.setYear(str1 + str2);
        }
    }

}
