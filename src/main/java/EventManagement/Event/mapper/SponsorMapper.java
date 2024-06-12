package EventManagement.Event.mapper;

import EventManagement.Event.entity.Sponsor;
import EventManagement.Event.DTO.SponsorDTO;

public class SponsorMapper {
    public static SponsorDTO toSponsorDto(Sponsor sponsor) {
        SponsorDTO sponsorDto = new SponsorDTO();
        sponsorDto.setId(sponsor.getId());
        sponsorDto.setInformation(sponsor.getInformation());
        sponsorDto.setCompanyName(sponsor.getCompanyName());
        sponsorDto.setCompanyID(sponsor.getCompanyID());
        sponsorDto.setFptStaffEmail(sponsor.getFptStaffEmail());
        return sponsorDto;
    }
}

