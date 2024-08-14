package cotato.growingpain.member.dto.response;

import cotato.growingpain.member.domain.MemberJob;
import cotato.growingpain.member.domain.entity.Member;

public record MemberInfoResponse(
        String field,
        String belong,
        MemberJob job,
        String educationBackground,
        String skill,
        String activityHistory,
        String award,
        String languageScore,
        String career,
        String aboutMe
) {
    public static MemberInfoResponse fromMember(Member member) {
        return new MemberInfoResponse(
                member.getField(),
                member.getBelong(),
                member.getJob(),
                member.getEducationBackground(),
                member.getSkill(),
                member.getActivityHistory(),
                member.getAward(),
                member.getLanguageScore(),
                member.getCareer(),
                member.getAboutMe()
        );
    }

    public static MemberInfoResponse defaultInfoFromMember(Member member) {
        return new MemberInfoResponse(
                member.getField(),
                member.getBelong(),
                member.getJob(),
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}