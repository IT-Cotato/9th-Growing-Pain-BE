package cotato.growingpain.log.domain.service;

import cotato.growingpain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class JobApplicationService {

    private final MemberRepository memberRepository;

}
