package com.example.spring;

import org.springframework.web.multipart.MultipartFile;

//画像データを格納するフォーム
public class ImageForm {
  private MultipartFile image;

  public MultipartFile getImage() {
      return image;
  }

  public void setImage(MultipartFile image) {
      this.image = image;
  }

}
