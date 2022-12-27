package ru.gb.worktaskmanager.managercore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.worktaskmanager.managercore.dtos.*;
import ru.gb.worktaskmanager.managercore.entites.Comment;
import ru.gb.worktaskmanager.managercore.mappers.CommentMapper;
import ru.gb.worktaskmanager.managercore.repositories.specifications.CommentSpecifications;
import ru.gb.worktaskmanager.managercore.services.CommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Контроллер комментариев
 */
@RestController
@RequestMapping("/comments")
@Validated
public class CommentController {
    private final CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping()
    public CommentListResponseDto getComments(@RequestBody @Valid CommentRequestDto requestDto) {
        //TODO по текущему пользователю
        //TODO описание свагера
        Specification<Comment> specification = CommentSpecifications.build(requestDto);
        int page = requestDto.getPage() == null ? 1 : requestDto.getPage();
        Page<Comment> taskPage = service.getComments(page, specification);
        //TODO DTO mapper
        List<CommentResponseDto> commentDtos = taskPage.getContent()
                .stream()
                .map(comment -> (new CommentMapper()).map(comment))
                .collect(Collectors.toList());

        return new CommentListResponseDto(commentDtos, page, taskPage.getTotalPages());
    }

    @PostMapping()
    public CommentResponseDto createComment(@RequestBody @Valid RequestCreateCommentDto createCommentDto) {
        //TODO Создание задания с подстановкой текущего пользователя
        //TODO описание свагера
        Comment newComment = service.createComment(createCommentDto);

        return (new CommentMapper()).map(newComment);
    }

    @PostMapping("/action")
    public boolean actionComment(@RequestBody @Valid ActionRequestDto requestDto) {
        //TODO Создание задания с подстановкой текущего пользователя
        //TODO описание свагера

        return service.actionComment(requestDto.getAction(), requestDto.getCommentId());
    }
}