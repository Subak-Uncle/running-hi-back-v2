package com.runninghi.runninghibackv2.application.dto.reply.response;

import com.runninghi.runninghibackv2.domain.entity.Reply;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CreateReplyResponse (

        @Schema(description = "댓글 번호", example = "2")
        Long replyNo,
        @Schema(description = "회원 닉네임", example = "러너1")
        String memberName,
        @Schema(description = "게시글 번호", example = "1")
        Long postNo,
        @Schema(description = "댓글 내용", example = "생성된 댓글 내용")
        String replyContent,
        @Schema(description = "댓글 삭제 여부", example = "false")
        boolean isDeleted,
        @Schema(description = "부모 댓글 번호", example = "1")
        Long parentReplyNo,
        @Schema(description = "댓글 생성 일", example = "2024-03-27T13:23:12")
        LocalDateTime createDate,
        @Schema(description = "댓글 수정 일", example = "2024-03-27T13:23:12")
        LocalDateTime updateDate
)
{
    public static CreateReplyResponse fromEntity (Reply reply) {
        return new CreateReplyResponse(
                reply.getReplyNo(),
                reply.getWriter().getNickname(),
                reply.getPost().getPostNo(),
                reply.getReplyContent(),
                reply.isDeleted(),
                reply.getParent().getReplyNo(),
                reply.getCreateDate(),
                reply.getUpdateDate()
        );
    }
}
