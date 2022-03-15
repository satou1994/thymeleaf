package com.example.spring;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//画像データの受け渡しするコントローラー
//処理としては、
//① imageFormに格納された画像データを取得し、byteの配列にする
//② それをbase64にエンコードし、且つString型に変更する
//③ 先に変更したStringと"data:image/jpeg;base64,"を組み合わせて<img ht:src="">で指定できる形にする
@Controller
public class UploadController {
  @ModelAttribute
  public ImageForm setForm() {
      return new ImageForm();
  }

  /**
   * まずlocalhost:8080/firstでupload.htmlにアクセスするよう設定
   * @return　upload.html
   */
	@RequestMapping(value="/upload",method = RequestMethod.GET)
  public String first() {
      return "upload";
  }


  /**
   * アップロードされた画像データを取得し、base64でエンコードする
   * エンコードしたものを文字列に変更(同時に拡張子をここではjpegと指定)し次のhtmlに受け渡す
   * @param imageForm アップロードされたデータ
   * @param model
   * @return
   * @throws Exception
   */
	@RequestMapping(value="/upload",method = RequestMethod.POST)
  public String upload(ImageForm imageForm, Model model) throws Exception {
//      System.out.println(imageForm.getImage().getSize());
      System.out.println(imageForm.getImage());
      StringBuffer data = new StringBuffer();
      String base64 = new String(Base64.encodeBase64(imageForm.getImage().getBytes()),"ASCII");//base64で画像をバイトコードに変換、Stringで保存
      data.append("data:image/jpeg;base64,");//htmlで表示するための、thymeleafを登録
      data.append(base64);//thymeleafで表示する画像のバイトコード登録
      model.addAttribute("base64image",data.toString());//パラメーターに渡す
      System.out.println("base64:"+ base64);
//      System.out.println("data:"+ data);
//      System.out.println("data.toString:"+ data.toString());
      return "upload";
  }

}
