package cotato.growingpain.member.dto.request;

import cotato.growingpain.member.domain.MemberJob;

public record UpdateDefaultInfoRequest (

        String name,

        String field,

        String belong,

        MemberJob job
) {
}