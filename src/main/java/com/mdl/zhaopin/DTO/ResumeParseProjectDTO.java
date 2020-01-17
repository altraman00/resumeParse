package com.mdl.zhaopin.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResumeParseProjectDTO implements Serializable {

    private String developEnvironment   ;
    private String developTool          ;
    private String dutyDesc[]           ;
    private String developDesc          ;
    private String projectDate          ;
    private String projectName          ;


    public void processTime() {
        if (this.projectDate != null && this.projectDate.contains(":00:00")) {
            String str1 = this.projectDate.substring(0, this.projectDate.indexOf(":00:00") - 3);
            this.projectDate = this.projectDate.substring(this.projectDate.indexOf(":00:00") + 6);
            String str2 = "";
            if (this.projectDate.contains(":00:00")) {
                str2 = this.projectDate.substring(0, this.projectDate.lastIndexOf(":00:00") - 3);
            }
            this.setProjectDate(str1 + str2);
        }
    }


}
