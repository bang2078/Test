package com.kh.bbs.web;


import com.kh.bbs.domain.board.svc.BoardSVC;
import com.kh.bbs.domain.endity.Board;
import com.kh.bbs.web.form.DetailForm;
import com.kh.bbs.web.form.SaveForm;
import com.kh.bbs.web.form.UpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

  final private BoardSVC boardSVC;

  //목록화면
  @GetMapping
  public String findAll(Model model) {
    List<Board> list = boardSVC.findAll();
    model.addAttribute("list", list);
    return "board/all";
  }

  //등록화면
  @GetMapping("/add")
  public String addForm(Model model) {
    model.addAttribute("saveForm", new SaveForm());
    return "board/add";
  }

  //등록처리
  @PostMapping("/add")
  public String add(@Valid @ModelAttribute SaveForm saveForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      // 유효성 검사 실패하면 다시 등록폼 보여주기
      return "board/add";
    }
    log.info("title={},author={},content={}", saveForm.getTitle(), saveForm.getAuthor(), saveForm.getContent());

    Board board = new Board();
    board.setTitle(saveForm.getTitle());
    board.setAuthor(saveForm.getAuthor());
    board.setContent(saveForm.getContent());

    Long bid = boardSVC.save(board);
    redirectAttributes.addAttribute("id", bid);
    return "redirect:/boards";
  }

  //조회
  @GetMapping("/{id}")
  public String findById(@PathVariable("id") Long id, Model model) {
    Optional<Board> optionalBoard = boardSVC.findById(id);
    Board findedBoard = optionalBoard.orElseThrow();

    DetailForm detailForm = new DetailForm();
    detailForm.setBoardId(findedBoard.getBoardId());
    detailForm.setTitle(findedBoard.getTitle());
    detailForm.setAuthor(findedBoard.getAuthor());
    detailForm.setContent(findedBoard.getContent());
    detailForm.setCreatedDate(findedBoard.getCreatedDate());
    detailForm.setModifiedDate(findedBoard.getModifiedDate());

    model.addAttribute("detailForm", detailForm);
    return "board/detailForm";
  }


  //삭제 단건
  @GetMapping("/{id}/delete")
  public String deleteById(@PathVariable("id") Long boardId) {

    int rows = boardSVC.deleteById(boardId);
    return "redirect:/boards";
  }

  //삭제 여러건
  @PostMapping("/delete")
  public String deleteByIds(@RequestParam("boardIds") List<Long> boardIds) {
    int rows = boardSVC.deleteByIds(boardIds);
    return "redirect:/boards";
  }

  //상품수정화면
  @GetMapping("/{id}/edit")
  public String updateForm(@PathVariable("id") Long boardId, Model model) {
    //1)유효성체크
    //상품조회
    Optional<Board> optionalBoard = boardSVC.findById(boardId);
    Board findedBoard = optionalBoard.orElseThrow();
    UpdateForm updateForm = new UpdateForm();
    updateForm.setBoardId(findedBoard.getBoardId());
    updateForm.setTitle(findedBoard.getTitle());
    updateForm.setContent(findedBoard.getContent());
    updateForm.setAuthor(findedBoard.getAuthor());
    updateForm.setCreatedDate(findedBoard.getCreatedDate());
    updateForm.setModifiedDate(findedBoard.getModifiedDate());
    model.addAttribute("updateForm", updateForm);
    return "board/updateForm";
  }

  //상품수정처리
  @PostMapping("/{id}/edit")
  public String updateById(
      @PathVariable("id") Long boardId,
      @Valid @ModelAttribute UpdateForm updateForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    log.info("id={}", boardId);
    log.info("updateForm={}", updateForm);

    //1)유효성 체크
    //1-1) 어노테이션 기반의 필드 검증
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);

      return "board/updateForm";
    }
    Board board = new Board();
    board.setBoardId(updateForm.getBoardId());
    board.setTitle(updateForm.getTitle());
    board.setContent(updateForm.getContent());
    board.setAuthor(updateForm.getAuthor());

    int rows = boardSVC.updateById(boardId, board);

    redirectAttributes.addAttribute("id",boardId);
    return "redirect:/boards/{id}";  // 302 get redirectUrl-> http://localhost/products/id
  }


  }
