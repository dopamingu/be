package com.dopamingu.be.domain.member.dao;

import com.dopamingu.be.domain.member.domain.Member;
import com.dopamingu.be.domain.member.domain.OauthInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByOauthInfo(OauthInfo oauthInfo);
}
