package com.kh.bbs.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveForm {
  @NotBlank(message = "제목은 필수 입니다.")
  @Size(min=1,max=16,message = "제목은 16자를 초과할 수 없습니다.")
  private String title;
  @NotBlank(message = "내용은 필수 입니다.")
  @Size(min=1,max=1300,message = "내용은 1300자를 초과할 수 없습니다.")
  private String content;
  @NotBlank(message = "작성자은 필수 입니다.")
  @Size(min=1,max=16,message = "작성자는 16자를 초과할 수 없습니다.")
  private String author;
}
