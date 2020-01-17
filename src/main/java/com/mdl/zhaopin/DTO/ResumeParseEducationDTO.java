package com.mdl.zhaopin.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResumeParseEducationDTO implements Serializable {

    private String educationTime    ;
    private String major            ;
    private String eduDegree        ;
    private String university       ;
    private String degree           ;

    public void processTime() {
        if (this.educationTime != null && this.educationTime.contains(":00:00")) {
            String str1 = this.educationTime.substring(0, this.educationTime.indexOf(":00:00") - 3);
            this.educationTime = this.educationTime.substring(this.educationTime.indexOf(":00:00") + 6);
            String str2 = "";
            if (this.educationTime.contains(":00:00")) {
                str2 = this.educationTime.substring(0, this.educationTime.lastIndexOf(":00:00") - 3);
            }
            this.setEducationTime(str1 + str2);
        }
    }
}
