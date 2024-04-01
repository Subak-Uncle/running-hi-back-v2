package com.runninghi.runninghibackv2.postreport.application.dto.response;

import com.runninghi.runninghibackv2.common.enumtype.ProcessingStatus;
import com.runninghi.runninghibackv2.common.enumtype.ReportCategory;
import com.runninghi.runninghibackv2.member.domain.aggregate.entity.Member;
import com.runninghi.runninghibackv2.post.domain.aggregate.entity.Post;
import com.runninghi.runninghibackv2.postreport.domain.aggregate.entity.PostReport;

public record UpdatePostReportResponse(
        Long postReportNo,
        ReportCategory category,
        String content,
        ProcessingStatus status,
        Long reporterNo,
        Long reportedPostNo,
        boolean isPostDeleted
) {
    public static UpdatePostReportResponse from(PostReport postReport) {

        return new UpdatePostReportResponse(
                postReport.getPostReportNo(),
                postReport.getCategory(),
                postReport.getContent(),
                postReport.getStatus(),
                postReport.getReporter().getMemberNo(),
                postReport.getReportedPost().getPostNo(),
                postReport.isPostDeleted());
    }
}
