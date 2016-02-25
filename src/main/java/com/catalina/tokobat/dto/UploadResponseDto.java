package com.catalina.tokobat.dto;

/**
 * Created by Alifa on 2/23/2016.
 */
public class UploadResponseDto extends  ResponseDto {

    private String imgPath;

    public UploadResponseDto(String message, long id) {
        super(message, id);
    }

    public UploadResponseDto(String message, long id, String path) {

        super(message, id);
        this.imgPath = path;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
