package com.kh.bbs.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateForm {
  private Long boardId;

  @NotBlank(message = "상품명은 필수 입니다.")
  @Size(min=1,max=50,message = "상품은 50자를 초과할 수 없습니다.")
  private String title;
  @NotBlank(message = "상품명은 필수 입니다.")
  @Size(min=1,max=4000,message = "상품은 4000자를 초과할 수 없습니다.")
  private String content;
  @NotBlank(message = "상품명은 필수 입니다.")
  @Size(min=1,max=50,message = "상품은 50자를 초과할 수 없습니다.")
  private String author;

  private Timestamp createdDate;
  private Timestamp modifiedDate; // ✅ camelCase로 수정
}

