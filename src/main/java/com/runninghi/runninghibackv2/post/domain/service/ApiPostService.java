package com.runninghi.runninghibackv2.post.domain.service;

import com.runninghi.runninghibackv2.member.domain.aggregate.entity.Member;

public interface ApiPostService {

    Member getMemberById(Long memberNo);

}
