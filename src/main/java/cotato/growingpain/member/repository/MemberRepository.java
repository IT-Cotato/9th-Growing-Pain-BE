package cotato.growingpain.member.repository;

import cotato.growingpain.member.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);
}