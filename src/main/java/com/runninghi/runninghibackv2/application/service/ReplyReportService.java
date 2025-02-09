package com.runninghi.runninghibackv2.application.service;

import com.runninghi.runninghibackv2.application.dto.replyreport.request.CreateReplyReportRequest;
import com.runninghi.runninghibackv2.application.dto.replyreport.response.CreateReplyReportResponse;
import com.runninghi.runninghibackv2.application.dto.replyreport.response.DeleteReplyReportResponse;
import com.runninghi.runninghibackv2.application.dto.replyreport.response.GetReplyReportResponse;
import com.runninghi.runninghibackv2.application.dto.replyreport.response.HandleReplyReportResponse;
import com.runninghi.runninghibackv2.domain.entity.Member;
import com.runninghi.runninghibackv2.domain.entity.Reply;
import com.runninghi.runninghibackv2.domain.entity.ReplyReport;
import com.runninghi.runninghibackv2.domain.enumtype.ProcessingStatus;
import com.runninghi.runninghibackv2.domain.repository.MemberRepository;
import com.runninghi.runninghibackv2.domain.repository.ReplyReportRepository;
import com.runninghi.runninghibackv2.domain.repository.ReplyRepository;
import com.runninghi.runninghibackv2.domain.service.ReplyReportChecker;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyReportService {

    private final ReplyReportRepository replyReportRepository;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;
    private final ReplyReportChecker replyReportChecker;

    // 댓글 신고 저장
    @Transactional
    public CreateReplyReportResponse createReplyReport(Long memberNo, CreateReplyReportRequest request) {

        replyReportChecker.checkReplyReportValidation(request);

        Member reporter = memberRepository.findByMemberNo(memberNo);
        Reply reportedReply = replyRepository.findById(request.reportedReplyNo())
                .orElseThrow(EntityNotFoundException::new);

        ReplyReport replyReport = ReplyReport.builder()
                .category(request.category())
                .content(request.content())
                .status(ProcessingStatus.INPROGRESS)
                .reporter(reporter)
                .reportedReply(reportedReply)
                .replyContent(reportedReply.getReplyContent())
                .isReplyDeleted(false)
                .build();

        replyReportRepository.save(replyReport);
        reportedReply.addReportedCount();

        return CreateReplyReportResponse.from(replyReport);
    }

    // 댓글 신고 전체 조회
    @Transactional(readOnly = true)
    public List<GetReplyReportResponse> getReplyReports() {

        return replyReportRepository.findAll().stream()
                .map(GetReplyReportResponse::from)
                .toList();
    }

    // 댓글 신고 상세 조회
    @Transactional(readOnly = true)
    public GetReplyReportResponse getReplyReportById(Long replyReportNo) {

        ReplyReport replyReport = replyReportRepository.findById(replyReportNo)
                .orElseThrow(EntityNotFoundException::new);

        return GetReplyReportResponse.from(replyReport);
    }

    // 신고된 댓글의 모든 신고 내역 조회
    @Transactional(readOnly = true)
    public Page<GetReplyReportResponse> getReplyReportScrollByReplyId(Long replyNo, Pageable pageable) {

        Reply reportedReply = replyRepository.findById(replyNo)
                .orElseThrow(EntityNotFoundException::new);

        return replyReportRepository.findAllByReportedReply(reportedReply, pageable)
                .map(GetReplyReportResponse::from);
    }

    // 댓글 신고 수락/거절 처리
    @Transactional
    public List<HandleReplyReportResponse> handleReplyReports(boolean isAccepted, Long replyNo) {

        Reply reportedReply = replyRepository.findById(replyNo)
                .orElseThrow(EntityNotFoundException::new);

        List<ReplyReport> replyReportList = replyReportRepository.findAllByReportedReply(reportedReply);
        ProcessingStatus status;

        if(isAccepted) {
            status = ProcessingStatus.ACCEPTED;
            Member reportedMember = reportedReply.getWriter();
            reportedMember.addReportedCount();

            replyReportList.forEach(replyReport -> replyReport.update(status, true, null));
            replyRepository.deleteById(replyNo);
        } else {
            status = ProcessingStatus.REJECTED;
            reportedReply.resetReportedCount();
            replyReportList.forEach(replyReport -> replyReport.update(status));
        }

        return replyReportList.stream()
                .map(HandleReplyReportResponse::from)
                .toList();
    }

    // 댓글 신고 삭제
    @Transactional
    public DeleteReplyReportResponse deleteReplyReport(Long replyReportNo) {
        replyReportRepository.deleteById(replyReportNo);

        return DeleteReplyReportResponse.from(replyReportNo);
    }
}
