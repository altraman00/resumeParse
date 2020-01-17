package com.mdl.zhaopin.DTO;

import lombok.Data;

import java.io.Serializable;


@Data
public class ResumeParseCertificateDTO implements Serializable {

    private String certificateName;
    private String time;

    public void processTime() {
        if (this.time != null && this.time.contains(":00:00")) {
            String str1 = this.time.substring(0, this.time.indexOf(":00:00") - 3);
            this.time = this.time.substring(this.time.indexOf(":00:00") + 6);
            String str2 = "";
            if (this.time.contains(":00:00")) {
                str2 = this.time.substring(0, this.time.lastIndexOf(":00:00") - 3);
            }
            this.setTime(str1 + str2);
        }
    }
}
